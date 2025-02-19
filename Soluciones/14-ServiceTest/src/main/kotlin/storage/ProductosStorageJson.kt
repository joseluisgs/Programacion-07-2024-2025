package dev.joseluisgs.storage

import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Almacenamiento de productos en JSON
 * Esta clase implementa la interfaz [ProductosStorageFile] para almacenar y leer productos en un fichero JSON.
 */
class ProductosStorageJson : ProductosStorageFile {
    private val logger = logging()
    
    init {
        logger.debug { "Inicializando almacenamiento de productos en JSON" }
    }
    
    /**
     * Lee los productos del fichero JSON y los devuelve como una lista de Producto
     * @param file Fichero JSON
     * @return Lista de productos
     * @throws ProductosException.ProductosStorageException Si el fichero no existe, no es un fichero o no se puede leer
     */
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero JSON: $file" }
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(".json")) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<List<ProductoDto>>(file.readText()).map { it.toModel() }
    }
    
    /**
     * Escribe los productos en un fichero JSON
     * @param file Fichero JSON
     * @param productos Lista de productos
     * @throws ProductosException.ProductosStorageException Si el directorio padre del fichero no existe
     */
    
    override fun writeToFile(productos: List<Producto>, file: File) {
        logger.debug { "Escribiendo productos en fichero JSON: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".json")) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
        file.writeText(json.encodeToString<List<ProductoDto>>(productos.map { it.toDto() }))
    }
}