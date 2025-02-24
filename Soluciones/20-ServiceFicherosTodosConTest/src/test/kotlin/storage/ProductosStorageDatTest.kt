package storage


import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.model.Producto
import dev.joseluisgs.storage.ProductosStorageDat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.ObjectOutputStream
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProductosStorageDatTest {
    
    private val storage: ProductosStorageDat = ProductosStorageDat()
    
    @Test
    @DisplayName("Debe leer productos de un fichero DAT correctamente")
    fun testReadFromFile(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.dat")
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        
        // Serializamos los productos en el fichero .dat
        file.outputStream().use { it ->
            val oos = ObjectOutputStream(it)
            oos.writeObject(productos.map { it.toDto() })
        }
        
        val productosLeidos = storage.readFromFile(file)
        
        assertAll(
            "productos",
            { assertEquals(1, productosLeidos.size, "Debería devolver un producto") },
            {
                assertEquals(
                    Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false),
                    productosLeidos[0],
                    "El producto no coincide"
                )
            }
        )
    }
    
    @Test
    @DisplayName("Debe lanzar una excepción si el fichero no existe")
    fun testReadFromFileNotFound(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.dat")
        
        val exception = assertFailsWith<ProductosException.ProductosStorageException> {
            storage.readFromFile(file)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El fichero no existe, o no es un fichero o no se puede leer: ${file.absolutePath}",
            exception.message, "El mensaje de error no coincide"
        )
    }
    
    @Test
    @DisplayName("Debe escribir productos en un fichero DAT correctamente")
    fun testWriteToFile(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.dat")
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        
        storage.writeToFile(productos, file)
        
        // Leer el contenido del archivo y verificar que es correcto
        val productosLeidos = storage.readFromFile(file)
        
        assertEquals(productos, productosLeidos, "El contenido del fichero no coincide")
    }
    
    @Test
    @DisplayName("Debe lanzar una excepción si el directorio padre no existe al escribir")
    fun testWriteToFileParentDirNotFound(@TempDir tempDir: File) {
        val file = File(tempDir, "nonexistent_dir/productos.dat")
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        
        val exception = assertFailsWith<ProductosException.ProductosStorageException> {
            storage.writeToFile(productos, file)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El directorio padre del fichero no existe: ${file.parentFile.absolutePath}",
            exception.message,
            "El mensaje de error no coincide"
        )
    }
}
