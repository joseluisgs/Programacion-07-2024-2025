package storage

import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import dev.joseluisgs.storage.ProductosStorageCsv
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProductosStorageCsvTest {
    
    private val storage: ProductosStorageCsv = ProductosStorageCsv()
    
    
    @Test
    @DisplayName("Debe leer productos de un fichero CSV correctamente")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testReadFromFile(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.csv")
        file.writeText(
            "productID,productName,supplierID,categoryID,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued\n" +
                    "1,Producto 1,10,10,10,10.0,10,10,10,0\n"
        )
        
        val productos = storage.readFromFile(file)
        
        assertAll(
            "productos",
            { assertEquals(1, productos.size, "Debería devolver un producto") },
            {
                assertEquals(
                    Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false),
                    productos[0],
                    "El producto no coincide"
                )
            }
        )
    }
    
    @Test
    @DisplayName("Debe lanzar una excepción si el fichero no existe")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testReadFromFileNotFound(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.csv")
        
        val exception = assertFailsWith<ProductosException.ProductosStorageException> {
            storage.readFromFile(file)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El fichero no existe, o no es un fichero o no se puede leer. Fichero: ${file.absolutePath}",
            exception.message, "El mensaje de error no coincide"
        )
    }
    
    @Test
    @DisplayName("Debe escribir productos en un fichero CSV correctamente")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testWriteToFile(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.csv")
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        
        storage.writeToFile(file, productos)
        
        val expectedContent =
            "productID,productName,supplierID,categoryID,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued\n" +
                    "1,Producto 1,10,10,10,10.0,10,10,10,0\n"
        assertEquals(expectedContent, file.readText(), "El contenido del fichero no coincide")
    }
    
    @Test
    @DisplayName("Debe lanzar una excepción si el directorio padre no existe al escribir")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testWriteToFileParentDirNotFound(@TempDir tempDir: File) {
        val file = File(tempDir, "nonexistent_dir/productos.csv")
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        
        val exception = assertFailsWith<ProductosException.ProductosStorageException> {
            storage.writeToFile(file, productos)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El directorio padre del fichero no existe. Fichero: ${file.parentFile.absolutePath}",
            exception.message,
            "El mensaje de error no coincide"
        )
    }
}