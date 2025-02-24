package dev.joseluisgs.storage

import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import java.io.File

class ProductosStorageJson : ProductosStorage {
    private val logger = logging()
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero JSON: $file" }
        if (!file.exists() || !file.isFile || !file.canRead()) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<List<ProductoDto>>(file.readText()).map { it.toModel() }
    }
    
    override fun writeToFile(file: File, productos: List<Producto>) {
        logger.debug { "Escribiendo productos en fichero JSON: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
        file.writeText(json.encodeToString<List<ProductoDto>>(productos.map { it.toDto() }))
    }
}