package dev.joseluisgs.storage

import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import org.lighthousegames.logging.logging
import java.io.File
import java.io.RandomAccessFile

/**
 * Almacenamiento de productos en Bin
 * Esta clase implementa la interfaz [ProductosStorageFile] para almacenar y leer productos en un fichero Bin.
 */

class ProductosStorageBin : ProductosStorageFile {
    private val logger = logging()
    
    init {
        logger.debug { "Inicializando almacenamiento de productos en Bin" }
    }
    
    /**
     * Lee los productos de un fichero Bin
     * @param file Fichero Bin
     * @return Lista de productos
     * @throws ProductosException.ProductosStorageException, Si el fichero no existe, no es un fichero o no se puede leer
     */
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero Bin: $file" }
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(
                ".bin",
                true
            )
        ) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        val productos = mutableListOf<ProductoDto>()
        
        RandomAccessFile(file, "r").use { raf ->
            // Mientras no se haya llegado al final del fichero,
            // leer los datos de cada producto y añadirlos a la lista
            while (raf.filePointer < raf.length()) {
                val id = raf.readInt() // Lee el id, que es un entero en 4 bytes
                val nombre =
                    raf.readUTF() // Lee el nombre, que es una cadena de texto de longitud variable hasta el final de la línea
                val idProveedor = raf.readInt()
                val idCategoría = raf.readInt()
                val cantidadPorUnidad = raf.readUTF()
                val precioUnidad = raf.readDouble() // Lee el precio, que es un número decimal en 8 bytes
                val unidadesEnStock = raf.readInt()
                val unidadesEnPedido = raf.readInt()
                val nivelDeReabastecimiento = raf.readInt()
                val descontinuado = raf.readInt()
                
                val producto = ProductoDto(
                    id, nombre, idProveedor, idCategoría, cantidadPorUnidad,
                    precioUnidad, unidadesEnStock, unidadesEnPedido,
                    nivelDeReabastecimiento, descontinuado
                )
                productos.add(producto)
            }
        }
        return productos.map { it.toModel() }
    }
    
    /**
     * Escribe los productos en un fichero Bin
     * @param file Fichero bin
     * @param productos Lista de productos
     * @throws ProductosException.ProductosStorageException Si el directorio padre del fichero no existe
     */
    override fun writeToFile(productos: List<Producto>, file: File) {
        logger.debug { "Escribiendo productos en fichero Bin: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".bin", true)) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        
        val dtos = productos.map { it.toDto() }
        RandomAccessFile(file, "rw").use { raf ->
            raf.setLength(0) // Limpiar el archivo antes de escribir
            for (producto in dtos) {
                raf.writeInt(producto.id) // Escribe el id, que es un entero en 4 bytes
                raf.writeUTF(producto.nombre) // Escribe el nombre, que es una cadena de texto de longitud variable hasta el final de la línea
                raf.writeInt(producto.idProveedor)
                raf.writeInt(producto.idCategoría)
                raf.writeUTF(producto.cantidadPorUnidad)
                raf.writeDouble(producto.precioUnidad) // Escribe el precio, que es un número decimal en 8 bytes
                raf.writeInt(producto.unidadesEnStock)
                raf.writeInt(producto.unidadesEnPedido)
                raf.writeInt(producto.nivelDeReabastecimiento)
                raf.writeInt(producto.descontinuado)
            }
        }
    }
}