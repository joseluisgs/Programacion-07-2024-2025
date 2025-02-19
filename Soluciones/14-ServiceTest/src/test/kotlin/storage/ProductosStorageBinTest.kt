package storage

import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import dev.joseluisgs.storage.ProductosStorageBin
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class ProductosStorageBinTest {
    
    
    private val storage = ProductosStorageBin()
    
    @Test
    @DisplayName("Test escribir y leer productos en fichero Bin")
    fun testWriteAndReadProducts(@TempDir tempDir: File) {
        // Crear una lista de productos de ejemplo
        val productos = listOf(
            Producto(1, "Producto A", 1, 1, "10 unidades", 9.99, 100, 10, 5, true),
            Producto(2, "Producto B", 2, 2, "5 unidades", 19.99, 50, 5, 10, false)
        )
        
        // Crear un archivo temporal para pruebas
        val file = File(tempDir, "productos.bin")
        
        // Escribir productos en el archivo
        storage.writeToFile(productos, file)
        
        // Leer productos desde el archivo
        val productosLeidos = storage.readFromFile(file)
        
        // Verificar que los productos leídos son iguales a los originales
        assertEquals(productos, productosLeidos)
    }
    
    @Test
    @DisplayName("Test leer desde un fichero inexistente lanza excepción")
    fun testReadFromNonExistentFileThrowsException(@TempDir tempDir: File) {
        val file = File(tempDir, "inexistente.bin")
        
        val exception = assertThrows<ProductosException.ProductosStorageException> {
            storage.readFromFile(file)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El fichero no existe, o no es un fichero o no se puede leer: ${file.absolutePath}",
            exception.message
        )
    }
    
    @Test
    @DisplayName("Test escribir en un directorio inexistente lanza excepción")
    fun testWriteToNonExistentDirectoryThrowsException(@TempDir tempDir: File) {
        val productos = listOf(
            Producto(1, "Producto A", 1, 1, "10 unidades", 9.99, 100, 10, 5, false)
        )
        
        val file = File(tempDir, "inexistente/productos.bin")
        
        val exception = assertThrows<ProductosException.ProductosStorageException> {
            storage.writeToFile(productos, file)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El directorio padre del fichero no existe: ${file.parentFile.absolutePath}",
            exception.message
        )
    }
}
