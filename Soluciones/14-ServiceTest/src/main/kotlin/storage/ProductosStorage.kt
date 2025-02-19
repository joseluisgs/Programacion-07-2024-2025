package dev.joseluisgs.storage

import dev.joseluisgs.model.Producto
import java.io.File

interface ProductosStorage {
    fun readFromFile(file: File, format: FileFormat): List<Producto>
    fun writeToFile(productos: List<Producto>, file: File, format: FileFormat)
}