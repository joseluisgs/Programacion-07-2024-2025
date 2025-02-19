package repository

import dev.joseluisgs.model.Producto
import dev.joseluisgs.repository.ProductosRepositoryImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductosRepositoryImplTest {

    private lateinit var productosRepository: ProductosRepositoryImpl


    @BeforeEach
    fun setUp() {
        productosRepository = ProductosRepositoryImpl()
    }

    @Test
    fun `test getAll returns empty list when no products exist`() {
        val productos = productosRepository.getAll()
        assertTrue(productos.isEmpty(), "Debería devolver una lista vacía")
    }

    @Test
    fun `test save and getById`() {
        val productoTest = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        val savedProducto = productosRepository.save(productoTest)

        val retrievedProducto = productosRepository.getById(savedProducto.id)
        assertNotNull(retrievedProducto, "Producto no encontrado")
        assertEquals(savedProducto, retrievedProducto, "Producto no coincide")
    }

    @Test
    fun `test save assigns unique id`() {
        val productoTest = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        val productoTest2 = Producto(2, "Producto 2", 20, 20, "20", 20.0, 20, 20, 20, false)

        val savedProducto1 = productosRepository.save(productoTest)
        val savedProducto2 = productosRepository.save(productoTest2)

        assertNotEquals(savedProducto1.id, savedProducto2.id, "Los id no son únicos")
    }

    @Test
    fun `test update modifies existing product`() {
        val productoTest = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        val savedProducto = productosRepository.save(productoTest)

        val updatedProducto = productoTest.copy(nombre = "Updated Product", precioUnidad = 20.0)
        val result = productosRepository.update(savedProducto.id, updatedProducto)

        assertNotNull(result, "Producto no encontrado")
        assertEquals(updatedProducto.nombre, result?.nombre, "El nombre no coincide")
        assertEquals(updatedProducto.precioUnidad, result?.precioUnidad, "El precio no coincide")
    }

    @Test
    fun `test update returns null for non-existing product`() {
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        val result = productosRepository.update(999, producto)

        assertNull(result, "Producto no encontrado")
    }

    @Test
    fun `test delete removes product`() {
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        val savedProducto = productosRepository.save(producto)

        val deletedProducto = productosRepository.delete(savedProducto.id)
        assertNotNull(deletedProducto, "Producto no encontrado")

        val retrievedProducto = productosRepository.getById(savedProducto.id)
        assertNull(retrievedProducto, "Producto no eliminado")
    }

    @Test
    fun `test delete returns null for non-existing product`() {
        val result = productosRepository.delete(999)
        assertNull(result, "Producto no encontrado")
    }
}