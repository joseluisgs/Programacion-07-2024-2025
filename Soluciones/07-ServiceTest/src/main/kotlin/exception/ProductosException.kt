package dev.joseluisgs.exception

/**
 * Excepciones de productos
 * @param message Mensaje de error
 */
sealed class ProductosException(message: String) : Exception(message) {
    class ProductosStorageException(message: String) :
        ProductosException("Error en el almacenamiento de productos: $message")

    class ProductoNotFoundException(id: Int) : ProductosException("Producto no encontrado: $id")
    class ProductoInvalidoException(message: String) : ProductosException("Producto no válido: $message")
}