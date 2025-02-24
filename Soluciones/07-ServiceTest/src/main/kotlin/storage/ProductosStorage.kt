package dev.joseluisgs.storage

import dev.joseluisgs.model.Producto

interface ProductosStorage : Storage<Producto> {
    //fun readFromFile(file: File): List<Producto>
    //fun writeToFile(file: File, productos: List<Producto>)
}