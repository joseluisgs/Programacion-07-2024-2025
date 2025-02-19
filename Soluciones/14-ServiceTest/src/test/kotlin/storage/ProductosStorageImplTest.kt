package storage

import dev.joseluisgs.model.Producto
import dev.joseluisgs.storage.FileFormat
import dev.joseluisgs.storage.ProductosStorageFile
import dev.joseluisgs.storage.ProductosStorageImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ProductosStorageImplTest {
    
    @MockK
    private lateinit var storageJson: ProductosStorageFile
    
    @MockK
    private lateinit var storageCsv: ProductosStorageFile
    
    @MockK
    private lateinit var storageXml: ProductosStorageFile
    
    @MockK
    private lateinit var storageDat: ProductosStorageFile
    
    @MockK
    private lateinit var storageBin: ProductosStorageFile
    
    @InjectMockKs
    private lateinit var productosStorage: ProductosStorageImpl
    
    
    @Test
    @DisplayName("readFromFile debe delegar en la implementación correcta según el formato Csv")
    fun readFromFileDelegateCsv(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.csv")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageCsv.readFromFile(file) } returns productos
        
        val result = productosStorage.readFromFile(file, FileFormat.CSV)
        
        assertEquals(productos, result)
        verify(exactly = 1) { storageCsv.readFromFile(file) }
    }
    
    @Test
    @DisplayName("writeToFile debe delegar en la implementación correcta según el formato Csv")
    fun writetoFileDelegateCsv(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageJson.writeToFile(productos, file) } returns Unit
        
        productosStorage.writeToFile(productos, file, FileFormat.JSON)
        
        verify(exactly = 1) { storageJson.writeToFile(productos, file) }
    }
    
    @Test
    @DisplayName("readFromFile debe delegar en la implementación correcta según el formato Json")
    fun readFromFileDelegateJson(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageJson.readFromFile(file) } returns productos
        
        val result = productosStorage.readFromFile(file, FileFormat.JSON)
        
        assertEquals(productos, result)
        verify(exactly = 1) { storageJson.readFromFile(file) }
    }
    
    @Test
    @DisplayName("writeToFile debe delegar en la implementación correcta según el formato Json")
    fun writetoFileDelegateJson(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageJson.writeToFile(productos, file) } returns Unit
        
        productosStorage.writeToFile(productos, file, FileFormat.JSON)
        
        verify(exactly = 1) { storageJson.writeToFile(productos, file) }
    }
    
    @Test
    @DisplayName("readFromFile debe delegar en la implementación correcta según el formato Xml")
    fun readFromFileDelegateXml(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.xml")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageXml.readFromFile(file) } returns productos
        
        val result = productosStorage.readFromFile(file, FileFormat.XML)
        
        assertEquals(productos, result)
        verify(exactly = 1) { storageXml.readFromFile(file) }
    }
    
    @Test
    @DisplayName("writeToFile debe delegar en la implementación correcta según el formato Xml")
    fun writetoFileDelegateXml(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.xml")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageXml.writeToFile(productos, file) } returns Unit
        
        productosStorage.writeToFile(productos, file, FileFormat.XML)
        
        verify(exactly = 1) { storageXml.writeToFile(productos, file) }
    }
    
    @Test
    @DisplayName("readFromFile debe delegar en DAT cuando el formato es Dat")
    fun readFromFileToDat(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.dat")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageDat.readFromFile(file) } returns productos
        
        val result = productosStorage.readFromFile(file, FileFormat.DAT)
        
        assertEquals(productos, result)
        verify(exactly = 1) { storageDat.readFromFile(file) }
    }
    
    @Test
    @DisplayName("writeToFile debe delegar en DAT cuando el formato es Dat")
    fun writetoFileToDat(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.dat")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageDat.writeToFile(productos, file) } returns Unit
        
        productosStorage.writeToFile(productos, file, FileFormat.DAT)
        
        verify(exactly = 1) { storageDat.writeToFile(productos, file) }
    }
    
    @Test
    @DisplayName("readFromFile debe delegar en BIN cuando el formato es BIN")
    fun readFromFileToBin(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.bin")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageBin.readFromFile(file) } returns productos
        
        val result = productosStorage.readFromFile(file, FileFormat.BIN)
        
        assertEquals(productos, result)
        verify(exactly = 1) { storageBin.readFromFile(file) }
    }
    
    @Test
    @DisplayName("writeToFile debe delegar en BIN cuando el formato es BIN")
    fun writetoFileToBin(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.bin")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageBin.writeToFile(productos, file) } returns Unit
        
        productosStorage.writeToFile(productos, file, FileFormat.BIN)
        
        verify(exactly = 1) { storageBin.writeToFile(productos, file) }
    }
    
    @Test
    @DisplayName("readFromFile debe delegar en JSON cuando el formato es DEFAULT")
    fun readFromFileToDefault(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageJson.readFromFile(file) } returns productos
        
        val result = productosStorage.readFromFile(file, FileFormat.DEFAULT)
        
        assertEquals(productos, result)
        verify(exactly = 1) { storageJson.readFromFile(file) }
    }
    
    @Test
    @DisplayName("writeToFile debe delegar en JSON cuando el formato es DEFAULT")
    fun writeFileToDefault(@TempDir tempDir: File) {
        val file = File(tempDir, "productos.json")
        val productos = listOf(Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false))
        
        every { storageJson.writeToFile(productos, file) } returns Unit
        
        productosStorage.writeToFile(productos, file, FileFormat.DEFAULT)
        
        verify(exactly = 1) { storageJson.writeToFile(productos, file) }
    }
}