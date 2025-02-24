package mapper


import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.mapper.toDto
import dev.joseluisgs.mapper.toModel
import dev.joseluisgs.model.Producto
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductosMapperTest {
    
    @Test
    @DisplayName("Test Producto to Dto")
    fun productoToProductoDto() {
        val producto = Producto(
            id = 1,
            nombre = "Producto 1",
            idProveedor = 2,
            idCategoría = 3,
            cantidadPorUnidad = "10 unidades",
            precioUnidad = 100.0,
            unidadesEnStock = 50,
            unidadesEnPedido = 20,
            nivelDeReabastecimiento = 5,
            descontinuado = true
        )
        
        val productoDto = producto.toDto()
        
        assertAll(
            { assertEquals(producto.id, productoDto.id) },
            { assertEquals(producto.nombre, productoDto.nombre) },
            { assertEquals(producto.idProveedor, productoDto.idProveedor) },
            { assertEquals(producto.idCategoría, productoDto.idCategoría) },
            { assertEquals(producto.cantidadPorUnidad, productoDto.cantidadPorUnidad) },
            { assertEquals(producto.precioUnidad, productoDto.precioUnidad) },
            { assertEquals(producto.unidadesEnStock, productoDto.unidadesEnStock) },
            { assertEquals(producto.unidadesEnPedido, productoDto.unidadesEnPedido) },
            { assertEquals(producto.nivelDeReabastecimiento, productoDto.nivelDeReabastecimiento) },
            { assertEquals(1, productoDto.descontinuado) }
        )
    }
    
    @Test
    @DisplayName("Test ProductoDto to Producto")
    fun productoDtoToProducto() {
        val productoDto = ProductoDto(
            id = 1,
            nombre = "Producto 1",
            idProveedor = 2,
            idCategoría = 3,
            cantidadPorUnidad = "10 unidades",
            precioUnidad = 100.0,
            unidadesEnStock = 50,
            unidadesEnPedido = 20,
            nivelDeReabastecimiento = 5,
            descontinuado = 1
        )
        
        val producto = productoDto.toModel()
        
        assertAll(
            { assertEquals(productoDto.id, producto.id) },
            { assertEquals(productoDto.nombre, producto.nombre) },
            { assertEquals(productoDto.idProveedor, producto.idProveedor) },
            { assertEquals(productoDto.idCategoría, producto.idCategoría) },
            { assertEquals(productoDto.cantidadPorUnidad, producto.cantidadPorUnidad) },
            { assertEquals(productoDto.precioUnidad, producto.precioUnidad) },
            { assertEquals(productoDto.unidadesEnStock, producto.unidadesEnStock) },
            { assertEquals(productoDto.unidadesEnPedido, producto.unidadesEnPedido) },
            { assertEquals(productoDto.nivelDeReabastecimiento, producto.nivelDeReabastecimiento) },
            { assertEquals(true, producto.descontinuado) }
        )
    }
}