package dev.joseluisgs.storage

import dev.joseluisgs.model.Producto
import java.io.File

interface ProductosStorageFile {
    fun readFromFile(file: File): List<Producto>
    fun writeToFile(productos: List<Producto>, file: File)
}