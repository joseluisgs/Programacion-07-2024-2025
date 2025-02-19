package dev.joseluisgs.storage

import dev.joseluisgs.model.Producto
import java.io.File

interface ProductosStorage {
    fun readFromFile(file: File): List<Producto>
    fun writeToFile(file: File, productos: List<Producto>)
}