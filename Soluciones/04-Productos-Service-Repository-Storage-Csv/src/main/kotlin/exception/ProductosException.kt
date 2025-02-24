package dev.joseluisgs.exception

/**
 * Excepciones de productos
 * @param message Mensaje de error
 */
sealed class ProductosException(message: String) : Exception(message) {
    class ProductosStorageException(message: String) : ProductosException(message)
    class ProductoNotFoundException(message: String) : ProductosException(message)
    class ProductoInvalidoException(message: String) : ProductosException(message)
}