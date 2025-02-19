package dev.joseluisgs.storage

import dev.joseluisgs.dto.ProductosDto
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Almacenamiento de productos en XML
 * Esta clase implementa la interfaz [ProductosStorageFile] para almacenar y leer productos en un fichero XML.
 */
class ProductosStorageXml : ProductosStorageFile {
    private val logger = logging()
    
    init {
        logger.debug { "Inicializando almacenamiento de productos en XML" }
    }
    
    /**
     * Lee los productos de un fichero XML
     * @param file Fichero XML
     * @return Lista de productos
     * @throws ProductosException.ProductosStorageException Si el fichero no existe, no es un fichero o no se puede leer
     */
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero XML: $file" }
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(
                ".xml",
                true
            )
        ) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        val json = XML {}
        val xmlString = file.readText()
        val productosDto = json.decodeFromString<ProductosDto>(xmlString)
        val productosListDto = productosDto.products
        return productosListDto.map { it.toModel() }
    }
    
    /**
     * Escribe los productos en un fichero XML
     * @param file Fichero XML
     * @param productos Lista de productos
     * @throws ProductosException.ProductosStorageException Si el directorio padre del fichero no existe
     */
    override fun writeToFile(productos: List<Producto>, file: File) {
        logger.debug { "Escribiendo productos en fichero XML: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".xml", true)) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        val json = XML { indent = 4 }
        val productosListDto = productos.map { it.toDto() }
        val productosDto = ProductosDto(productosListDto)
        file.writeText(json.encodeToString<ProductosDto>(productosDto))
    }
}