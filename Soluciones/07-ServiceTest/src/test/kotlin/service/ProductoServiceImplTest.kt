package service

import dev.joseluisgs.cache.Cache
import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import dev.joseluisgs.repository.ProductosRepository
import dev.joseluisgs.service.ProductoServiceImpl
import dev.joseluisgs.storage.ProductosStorage
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ProductoServiceImplTest {
    @MockK
    private lateinit var storage: ProductosStorage

    @MockK
    private lateinit var repository: ProductosRepository

    @MockK
    private lateinit var cache: Cache<Int, Producto>

    @InjectMockKs
    private lateinit var productoService: ProductoServiceImpl


    @Test
    @DisplayName("Si consulto todos los productos debe devolver una lista de productos")
    fun testGetAllProducts() {
        // arrange
        every { repository.getAll() } returns listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )

        // act
        val productos = productoService.getAll()

        // assert
        assertAll(
            "productos",
            { assertEquals(1, productos.size, "Debería devolver un producto") },
            { assertEquals("Producto 1", productos[0].nombre, "El nombre no coincide") }
        )

        // verify
        verify(exactly = 1) { repository.getAll() }
    }

    @Test
    @DisplayName("Si busco un producto por id debe devolver el producto, si esta en cache no debe llamar al repositorio")
    fun testGetById() {
        // arrange
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        every { cache.get(1) } returns producto

        // act
        val productoResult = productoService.getById(1)

        // assert
        assertEquals(producto, productoResult, "El producto no coincide")

        // verify
        verify(exactly = 1) { cache.get(1) }
        verify(exactly = 0) { repository.getById(1) }
    }

    @Test
    @DisplayName("Si busco un producto por id y este no está en cache debe llamar al repositorio")
    fun testGetByIdFromRepository() {
        // arrange
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        every { cache.get(1) } returns null
        every { repository.getById(1) } returns producto
        every { cache.put(1, producto) } returns producto

        // act
        val productoResult = productoService.getById(1)

        // assert
        assertEquals(producto, productoResult, "El producto no coincide")

        // verify
        verify(exactly = 1) { cache.get(1) }
        verify(exactly = 1) { repository.getById(1) }
        verify(exactly = 1) { cache.put(1, producto) }
    }

    @Test
    @DisplayName("Buscar un producto por id que no existe debe lanzar una excepción")
    fun testGetByIdNotFound() {
        // arrange
        every { cache.get(1) } returns null
        every { repository.getById(1) } returns null

        // act & assert
        val res = assertThrows<ProductosException.ProductoNotFoundException> {
            productoService.getById(1)
        }

        assertEquals("Producto no encontrado: 1", res.message, "El mensaje de error no coincide")

        // verify
        verify(exactly = 1) { cache.get(1) }
        verify(exactly = 1) { repository.getById(1) }
    }

    @Test
    @DisplayName("Al crear un producto debe guardarlo en el repositorio")
    fun testCreateProduct() {
        // arrange
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        every { repository.save(producto) } returns producto

        // act
        val productoResult = productoService.save(producto)

        // assert
        assertEquals(producto, productoResult, "El producto no coincide")

        // verify
        verify(exactly = 1) { repository.save(producto) }
    }

    @Test
    @DisplayName("Al actualizar un producto debe guardarlo en el repositorio y eliminar de la cache")
    fun testUpdateProduct() {
        // arrange
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        every { repository.update(1, producto) } returns producto
        every { cache.remove(1) } returns null // o producto

        // act
        val productoResult = productoService.update(1, producto)

        // assert
        assertEquals(producto, productoResult, "El producto no coincide")

        // verify
        verify(exactly = 1) { repository.update(1, producto) }
        verify(exactly = 1) { cache.remove(1) }
    }

    @Test
    @DisplayName("Al actualizar un producto que no existe debe lanzar una excepción")
    fun testUpdateProductNotFound() {
        // arrange
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        every { repository.update(1, producto) } returns null

        // act & assert
        val res = assertThrows<ProductosException.ProductoNotFoundException> {
            productoService.update(1, producto)
        }

        assertEquals("Producto no encontrado: 1", res.message, "El mensaje de error no coincide")

        // verify
        verify(exactly = 1) { repository.update(1, producto) }
    }

    @Test
    @DisplayName("Al borrar un producto debe eliminarlo del repositorio y de la cache")
    fun testDeleteProduct() {
        // arrange
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        every { repository.delete(1) } returns producto
        every { cache.remove(1) } returns null // o producto

        // act
        val productoResult = productoService.delete(1)

        // assert
        assertEquals(producto, productoResult, "El producto no coincide")

        // verify
        verify(exactly = 1) { repository.delete(1) }
        verify(exactly = 1) { cache.remove(1) }
    }

    @Test
    @DisplayName("Al borrar un producto que no existe debe lanzar una excepción")
    fun testDeleteProductNotFound() {
        // arrange
        every { repository.delete(1) } returns null

        // act & assert
        val res = assertThrows<ProductosException.ProductoNotFoundException> {
            productoService.delete(1)
        }

        assertEquals("Producto no encontrado: 1", res.message, "El mensaje de error no coincide")

        // verify
        verify(exactly = 1) { repository.delete(1) }
    }


    @Test
    @DisplayName("Al exportar los productos debe llamar al repositorio y escribir en el fichero")
    fun testExportToFile() {
        // arrange
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        every { repository.getAll() } returns productos
        every { storage.writeToFile(File("productos.txt"), productos) } returns Unit

        // act
        productoService.exportToFile("productos.txt")

        // verify
        verify(exactly = 1) { repository.getAll() }
        verify(exactly = 1) { storage.writeToFile(File("productos.txt"), productos) }
    }


    @Test
    @DisplayName("Al importar los productos debe leer del fichero y guardar en el repositorio")
    fun testImportFromFile() {
        // arrange
        val productos = listOf(
            Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        )
        every { storage.readFromFile(File("productos.txt")) } returns productos
        every { repository.save(any()) } returns productos[0] // Usamos any() porque no podemos comparar objetos, si no lo entiendes pregunta :)

        // act
        productoService.importFromFile("productos.txt")

        // verify
        verify(exactly = 1) { storage.readFromFile(File("productos.txt")) }
        verify(exactly = 1) { repository.save(any()) } // Usamos any() porque no podemos comparar objetos
    }

}