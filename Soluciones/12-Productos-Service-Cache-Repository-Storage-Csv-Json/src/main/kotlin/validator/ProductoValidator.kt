package dev.joseluisgs.validator

import dev.joseluisgs.exception.ProductosException
import dev.joseluisgs.model.Producto

/**
 * Valida un producto
 * @throws ProductosException.ProductoInvalidoException
 * @receiver Producto
 */
fun Producto.validate() {
    // el nombre no esté en blanco
    //la cantidad por unidad sea positiva y mayor a cero
    //el precio unitario sea mayor a cero.
    if (nombre.isBlank()) {
        throw ProductosException.ProductoInvalidoException("El nombre no puede estar en blanco")
    }
    if (cantidadPorUnidad.isBlank()) {
        throw ProductosException.ProductoInvalidoException("La cantidad por unidad no puede estar en blanco")
    }
    if (precioUnidad <= 0) {
        throw ProductosException.ProductoInvalidoException("El precio unitario no puede ser negativo")
    }
}