package dev.joseluisgs.storage

import dev.joseluisgs.model.Producto
import org.lighthousegames.logging.logging

import java.io.File

class ProductosStorageImpl(
    private val storageJson: ProductosStorageFile = ProductosStorageJson(), // Inyectamos implementaciones específicas para cada formato de archivo y valor por defecto
    private val storageCsv: ProductosStorageFile = ProductosStorageCsv(), // Inyectamos implementaciones específicas para cada formato de archivo y valor por defecto
    private val storageXml: ProductosStorageFile = ProductosStorageXml() // Inyectamos implementaciones específicas para cada formato de archivo y valor por defecto
) : ProductosStorage {
    private val logger = logging()
    override fun readFromFile(file: File, format: FileFormat): List<Producto> {
        logger.debug { "Leyendo productos de fichero: $file" }
        return when (format) {
            FileFormat.JSON -> storageJson.readFromFile(file)
            FileFormat.CSV -> storageCsv.readFromFile(file)
            FileFormat.XML -> storageXml.readFromFile(file)
        }
    }
    
    override fun writeToFile(productos: List<Producto>, file: File, format: FileFormat) {
        logger.debug { "Escribiendo productos en fichero: $file" }
        when (format) {
            FileFormat.JSON -> storageJson.writeToFile(productos, file)
            FileFormat.CSV -> storageCsv.writeToFile(productos, file)
            FileFormat.XML -> storageXml.writeToFile(productos, file)
        }
    }
}