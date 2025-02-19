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

class ProductosStorageXml : ProductosStorageFile {
    private val logger = logging()
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero XML: $file" }
        if (!file.exists() || !file.isFile || !file.canRead()) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        val json = XML {}
        val xmlString = file.readText()
        val productosDto = json.decodeFromString<ProductosDto>(xmlString)
        val productosListDto = productosDto.products
        return productosListDto.map { it.toModel() }
    }
    
    override fun writeToFile(productos: List<Producto>, file: File) {
        logger.debug { "Escribiendo productos en fichero XML: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        val json = XML { indent = 4 }
        val productosListDto = productos.map { it.toDto() }
        val productosDto = ProductosDto(productosListDto)
        file.writeText(json.encodeToString<ProductosDto>(productosDto))
    }
}