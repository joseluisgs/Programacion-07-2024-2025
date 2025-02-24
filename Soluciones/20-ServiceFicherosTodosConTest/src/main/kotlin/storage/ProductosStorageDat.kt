package dev.joseluisgs.storage

import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import org.lighthousegames.logging.logging
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Almacenamiento de productos en DAT
 * Esta clase implementa la interfaz [ProductosStorageFile] para almacenar y leer productos en un fichero DAT.
 */

class ProductosStorageDat : ProductosStorageFile {
    private val logger = logging()
    
    init {
        logger.debug { "Inicializando almacenamiento de productos en Dat" }
    }
    
    /**
     * Lee los productos de un fichero Dat
     * @param file Fichero Dat
     * @return Lista de productos
     * @throws ProductosException.ProductosStorageException, Si el fichero no existe, no es un fichero o no se puede leer
     */
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero Dat: $file" }
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(
                ".dat",
                true
            )
        ) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        // Desarialzamos el fichero Dat
        var dtos: List<ProductoDto>
        // Serializamos el fichero Dat, y devolvemos una vez terminado, use cierra el flujo
        file.inputStream().use { it ->
            val ois = ObjectInputStream(it)
            dtos = ois.readObject() as List<ProductoDto>
        }
        return dtos.map { it.toModel() }
    }
    
    /**
     * Escribe los productos en un fichero Dat
     * @param file Fichero Dat
     * @param productos Lista de productos
     * @throws ProductosException.ProductosStorageException
     */
    override fun writeToFile(productos: List<Producto>, file: File) {
        logger.debug { "Escribiendo productos en fichero Dat: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".dat", true)) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        // Serializamos el fichero Dat
        // Serializamos el fichero Dat, y devolvemos una vez terminado, use cierra el flujo
        file.outputStream().use { it ->
            val oos = ObjectOutputStream(it)
            oos.writeObject(productos.map { it.toDto() })
        }
    }
}