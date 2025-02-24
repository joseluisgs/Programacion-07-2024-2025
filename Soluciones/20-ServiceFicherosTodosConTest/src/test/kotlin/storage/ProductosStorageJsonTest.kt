package storage

import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import dev.joseluisgs.storage.ProductosStorageJson
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProductosStorageJsonTest {
    
    private val storage: ProductosStorageJson = ProductosStorageJson()
    
    
    @Test
    @DisplayName("Debe leer productos de un fichero Json correctamente")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testReadFromFile(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        // Cuidado con las tabulaciones...
        file.writeText(
            """
            [
               {
                "productID": 1,
                "productName": "Producto 1",
                "supplierID": 10,
                "categoryID": 10,
                "quantityPerUnit": "10",
                "unitPrice": 10,
                "unitsInStock": 10,
                "unitsOnOrder": 10,
                "reorderLevel": 10,
                "discontinued": 0
              }
            ]
            """.trimIndent()
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
        val file = File(tempDir, "productos.json")
        
        val exception = assertFailsWith<ProductosException.ProductosStorageException> {
            storage.readFromFile(file)
        }
        
        assertEquals(
            "Error en el almacenamiento de productos: El fichero no existe, o no es un fichero o no se puede leer: ${file.absolutePath}",
            exception.message, "El mensaje de error no coincide"
        )
    }
    
    @Test
    @DisplayName("Debe escribir productos en un fichero Json correctamente")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testWriteToFile(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        
        storage.writeToFile(productos, file)
        // Cuidado con las tabulaciones...
        val expectedContent =
            """
            [
                {
                    "productID": 1,
                    "productName": "Producto 1",
                    "supplierID": 10,
                    "categoryID": 10,
                    "quantityPerUnit": "10",
                    "unitPrice": 10.0,
                    "unitsInStock": 10,
                    "unitsOnOrder": 10,
                    "reorderLevel": 10,
                    "discontinued": 0
                }
            ]
           """.trimIndent()
        assertEquals(expectedContent, file.readText(), "El contenido del fichero no coincide")
    }
    
    @Test
    @DisplayName("Debe lanzar una excepción si el directorio padre no existe al escribir")
    // Le pasamos un directorio temporal para que cree el fichero, lo lea y lo borre al finalizar
    fun testWriteToFileParentDirNotFound(@TempDir tempDir: File) {
        val file = File(tempDir, "nonexistent_dir/productos.json")
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