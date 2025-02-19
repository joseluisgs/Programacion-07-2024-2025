package dev.joseluisgs.services

import dev.joseluisgs.model.Producto

interface ProductoService {
    //private fun readFromFile(filePath: String): List<Producto>
    //fun writeToFile(filePath: String, productos: List<Producto>)

    fun importFromFile(filePath: String)
    fun exportToFile(filePath: String)

    fun getAll(): List<Producto>
    fun getById(id: Int): Producto
    fun save(producto: Producto): Producto
    fun update(id: Int, producto: Producto): Producto
    fun delete(id: Int): Producto
}