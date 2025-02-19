package validator

import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto
import dev.joseluisgs.validator.validate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ProductoValidatorKtTest {

    @Test
    fun validateIsOk() {
        val producto = Producto(1, "Producto 1", 10, 10, "10", 10.0, 10, 10, 10, false)
        assertDoesNotThrow { producto.validate() }
    }

    @Test
    fun validateThrowsExceptionWithNombre() {
        val producto = Producto(1, "", 10, 10, "10", 10.0, 10, 10, 10, false)
        val res = assertThrows<ProductosException.ProductoInvalidoException> { producto.validate() }
        assertEquals("Producto no válido: El nombre no puede estar en blanco", res.message, "El mensaje no es correcto")
    }

    @Test
    fun validateThrowsExceptionWithPrecio() {
        val producto = Producto(1, "Producto 1", 10, 10, "10", -10.0, 10, 10, 10, false)
        val res = assertThrows<ProductosException.ProductoInvalidoException> { producto.validate() }
        assertEquals(
            "Producto no válido: El precio unitario no puede ser negativo",
            res.message,
            "El mensaje no es correcto"
        )
    }

    @Test
    fun validateThrowsExceptionWithCantidadPorUnidad() {
        val producto = Producto(1, "Producto 1", 10, 10, "", 10.0, 10, 10, 10, false)
        val res = assertThrows<ProductosException.ProductoInvalidoException> { producto.validate() }
        assertEquals(
            "Producto no válido: La cantidad por unidad no puede estar en blanco",
            res.message,
            "El mensaje no es correcto"
        )
    }
}