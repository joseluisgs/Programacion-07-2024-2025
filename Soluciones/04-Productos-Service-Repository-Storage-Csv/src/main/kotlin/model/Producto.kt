package dev.joseluisgs.model

import dev.joseluisgs.utils.roundTo

/**
 * Producto de la tienda
 * @property id Int, Identificador del producto
 * @property nombre String, Nombre del producto
 * @property idProveedor Int, Identificador del proveedor
 * @property idCategoría Int, Identificador de la categoría
 * @property cantidadPorUnidad String, Cantidad por unidad
 * @property precioUnidad Double, Precio por unidad
 * @property unidadesEnStock Int, Unidades en stock
 * @property unidadesEnPedido Int, Unidades en pedido
 * @property nivelDeReabastecimiento Int, Nivel de reabastecimiento
 * @property descontinuado Boolean, Descontinuado o o no
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val idProveedor: Int,
    val idCategoría: Int,
    val cantidadPorUnidad: String,
    val precioUnidad: Double,
    val unidadesEnStock: Int,
    val unidadesEnPedido: Int,
    val nivelDeReabastecimiento: Int,
    val descontinuado: Boolean,
) {
    override fun toString(): String {
        return "Producto(id=$id, nombre='$nombre', idProveedor=$idProveedor, idCategoría=$idCategoría, cantidadPorUnidad='$cantidadPorUnidad', precioUnidad=${
            precioUnidad.roundTo(
                2
            )
        }, unidadesEnStock=$unidadesEnStock, unidadesEnPedido=$unidadesEnPedido, nivelDeReabastecimiento=$nivelDeReabastecimiento, descontinuado=$descontinuado)"
    }
}