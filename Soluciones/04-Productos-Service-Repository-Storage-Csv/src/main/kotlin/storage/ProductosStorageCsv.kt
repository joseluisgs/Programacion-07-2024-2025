package dev.joseluisgs.storage

import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Almacenamiento de productos en CSV
 * Esta clase implementa la interfaz [ProductosStorage] para almacenar y leer productos en un fichero CSV.
 */
class ProductosStorageCsv : ProductosStorage {
    private val logger = logging()

    /**
     * Lee los productos de un fichero CSV
     * @param file Fichero CSV
     * @return Lista de productos
     * @throws ProductosException.ProductosStorageException
     */
    override fun readFromFile(file: File): List<Producto> {
        logger.debug { "Leyendo productos de fichero CSV: $file" }
        if (!file.exists() || !file.isFile || !file.canRead()) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw ProductosException.ProductosStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }
        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { it.map { it.trim() } }
            .map {
                Producto(
                    it[0].toInt(),
                    it[1],
                    it[2].toInt(),
                    it[3].toInt(),
                    it[4],
                    it[5].toDouble(),
                    it[6].toInt(),
                    it[7].toInt(),
                    it[8].toInt(),
                    it[9].toBoolean()
                )
            }
    }

    /**
     * Escribe los productos en un fichero CSV
     * @param file Fichero CSV
     * @param productos Lista de productos
     * @throws ProductosException.ProductosStorageException
     */
    override fun writeToFile(file: File, productos: List<Producto>) {
        logger.debug { "Escribiendo productos en fichero CSV: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw ProductosException.ProductosStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }
        file.writeText("productID,productName,supplierID,categoryID,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued\n")
        productos.forEach {
            file.appendText("${it.id},${it.nombre},${it.idProveedor},${it.idCategoría},${it.cantidadPorUnidad},${it.precioUnidad},${it.unidadesEnStock},${it.unidadesEnPedido},${it.nivelDeReabastecimiento},${it.descontinuado}\n")
        }
    }
}