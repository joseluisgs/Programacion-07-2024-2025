package dev.joseluisgs.services

import dev.joseluisgs.model.Producto
import dev.joseluisgs.storage.ExportFormat

interface ProductoService {
    
    fun importFromFile(filePath: String, format: ExportFormat = ExportFormat.JSON)
    fun exportToFile(filePath: String, format: ExportFormat = ExportFormat.JSON)
    
    fun getAll(): List<Producto>
    fun getById(id: Int): Producto
    fun save(producto: Producto): Producto
    fun update(id: Int, producto: Producto): Producto
    fun delete(id: Int): Producto
    
    
}