package dev.joseluisgs.storage

import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Almacenamiento de productos en CSV
 * Esta clase implementa la interfaz [ProductosStorage] para almacenar y leer productos en un fichero CSV.
 */
class ProductosStorageCsv : ProductosStorageFile {
    private val logger = logging()
    
    init {
        logger.debug { "Inicializando almacenamiento de productos en CSV" }
    }
    
    /**
     * Lee los productos de un fichero CSV
     * @param file Fichero CSV
     * @return Lista de productos
     * @throws ProductosException.ProductosStorageException Si el fichero no existe, no es un fichero o no se puede leer
     */
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero CSV: $file" }
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(
                ".csv",
                true
            )
        ) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { it.map { it.trim() } }
            .map {
                ProductoDto(
                    it[0].toInt(),
                    it[1],
                    it[2].toInt(),
                    it[3].toInt(),
                    it[4],
                    it[5].toDouble(),
                    it[6].toInt(),
                    it[7].toInt(),
                    it[8].toInt(),
                    it[9].toInt()
                ).toModel()
            }
    }
    
    /**
     * Escribe los productos en un fichero CSV
     * @param file Fichero CSV
     * @param productos Lista de productos
     * @throws ProductosException.ProductosStorageException Si el directorio padre del fichero no existe
     */
    override fun writeToFile(productos: List<Producto>, file: File) {
        logger.debug { "Escribiendo productos en fichero CSV: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".csv", true)) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        file.writeText("productID,productName,supplierID,categoryID,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued\n")
        productos.map { it.toDto() }.forEach {
            file.appendText("${it.id},${it.nombre},${it.idProveedor},${it.idCategoría},${it.cantidadPorUnidad},${it.precioUnidad},${it.unidadesEnStock},${it.unidadesEnPedido},${it.nivelDeReabastecimiento},${it.descontinuado}\n")
        }
    }
}