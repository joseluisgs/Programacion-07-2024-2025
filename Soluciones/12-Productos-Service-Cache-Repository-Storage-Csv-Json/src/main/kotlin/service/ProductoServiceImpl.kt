package dev.joseluisgs.service

import dev.joseluisgs.cache.CacheImpl
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import dev.joseluisgs.repository.ProductosRepository
import dev.joseluisgs.services.ProductoService
import dev.joseluisgs.storage.ExportFormat
import dev.joseluisgs.storage.ProductosStorage
import dev.joseluisgs.validator.validate
import org.lighthousegames.logging.logging
import java.io.File

// Opcional, puedes menter una cache!!!

/**
 * Implementación del servicio de productos
 * Esta clase implementa la interfaz [ProductoService] para gestionar [Producto]s.
 * Además usa una cache para mejorar la performance al leer productos.
 * @property storage Almacenamiento de productos, para leer y escribir en ficheros
 * @property repository Repositorio de productos, para almacenar y gestionar productos en memoria
 * @constructor Crea un servicio de productos con un almacenamiento y un repositorio
 * @see ProductoService
 * @see ProductosStorage
 * @see ProductosRepository
 */

private const val CACHE_SIZE = 5

class ProductoServiceImpl(
    private val storageCsv: ProductosStorage, // Inyectamos el almacenamiento
    private val storageJson: ProductosStorage, // Inyectamos el almacenamiento
    private val storageXml: ProductosStorage, // Inyectamos el almacenamiento
    private val repository: ProductosRepository // Inyectamos el repositorio
) : ProductoService {
    private val logger = logging()
    private val cache =
        CacheImpl<Int, Producto>(
            CACHE_SIZE,
            CacheImpl.CacheType.LRU
        )
    
    init {
        logger.debug { "Inicializando servicio de productos" }
    }
    
    /**
     * Lee los productos de un fichero
     * @param filePath Ruta del fichero
     * @return Lista de productos
     */
    private fun readFromFile(filePath: String, format: ExportFormat): List<Producto> {
        logger.info { "Leyendo productos de fichero: $filePath" }
        return when (format) {
            ExportFormat.CSV -> storageCsv.readFromFile(File(filePath))
            ExportFormat.JSON -> storageJson.readFromFile(File(filePath))
            ExportFormat.XML -> storageXml.readFromFile(File(filePath))
        }
    }
    
    /**
     * Escribe los productos en un fichero
     * @param filePath Ruta del fichero
     * @param productos Lista de productos
     */
    private fun writeToFile(filePath: String, productos: List<Producto>, format: ExportFormat) {
        logger.info { "Escribiendo productos en fichero: $filePath" }
        when (format) {
            ExportFormat.CSV -> storageCsv.writeToFile(File(filePath), productos)
            ExportFormat.JSON -> storageJson.writeToFile(File(filePath), productos)
            ExportFormat.XML -> storageXml.writeToFile(File(filePath), productos)
        }
    }
    
    /**
     * Importa los productos de un fichero y los almacena en el repositorio
     * @param filePath Ruta del fichero
     */
    override fun importFromFile(filePath: String, format: ExportFormat) {
        logger.info { "Importando productos de fichero: $filePath" }
        val productos = readFromFile(filePath, format)
        productos.forEach {
            repository.save(it)
        }
    }
    
    /**
     * Exporta los productos del repositorio a un fichero
     * @param filePath Ruta del fichero
     */
    override fun exportToFile(filePath: String, format: ExportFormat) {
        logger.info { "Exportando productos a fichero: $filePath" }
        writeToFile(filePath, repository.getAll(), format)
    }
    
    
    /**
     * Obtiene todos los productos
     * @return Lista de productos
     */
    override fun getAll(): List<Producto> {
        logger.info { "Obteniendo todos los productos" }
        return repository.getAll()
    }
    
    /**
     * Obtiene un producto por su id
     * @param id Identificador del producto
     * @return Producto con el id especificado
     * @throws ProductosException.ProductoNotFoundException
     */
    override fun getById(id: Int): Producto {
        logger.info { "Obteniendo producto con id: $id" }
        return cache.get(id) ?: run {
            val producto = repository.getById(id)
                ?: throw ProductosException.ProductoNotFoundException(id)
            // solo vamos a almacenarlo en la cache cuando se consulta no cuando se guarda o actualiza
            cache.put(id, producto)
            producto
        }
    }
    
    /**
     * Guarda un producto
     * @param producto Producto a guardar
     * @return Producto guardado
     * @throws ProductosException.ProductoValidationException si el producto no es válido
     */
    override fun save(producto: Producto): Producto {
        logger.info { "Guardando producto: $producto" }
        producto.validate() // Validamos el producto
        return repository.save(producto) // Guardamos el producto
    }
    
    /**
     * Actualiza un producto por su id
     * @param id Identificador del producto
     * @param producto Producto a actualizar
     * @return Producto actualizado
     * @throws ProductosException.ProductoNotFoundException si el producto no se encuentra
     * @throws ProductosException.ProductoValidationException si el producto no es válido
     */
    override fun update(id: Int, producto: Producto): Producto {
        logger.info { "Actualizando producto con id: $id" }
        producto.validate() // Validamos el producto
        return repository.update(id, producto) // Actualizamos el producto
            ?.also { cache.remove(id) } // Borramos de la cache, ya que se ha actualizado
            ?: throw ProductosException.ProductoNotFoundException(id)
    }
    
    /**
     * Borra un producto por su id
     * @param id Identificador del producto
     * @return Producto borrado
     * @throws ProductosException.ProductoNotFoundException si el producto no se encuentra
     */
    override fun delete(id: Int): Producto {
        logger.info { "Borrando producto con id: $id" }
        return repository.delete(id)
            ?.also { cache.remove(id) } // Borramos de la cache, ya que no existe
            ?: throw ProductosException.ProductoNotFoundException(id)
    }
}