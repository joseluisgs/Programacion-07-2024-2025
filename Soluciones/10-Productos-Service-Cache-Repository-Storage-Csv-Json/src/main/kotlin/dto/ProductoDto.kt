package dev.joseluisgs.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductoDto(
    @SerialName("productID")
    val id: Int = 0,
    @SerialName("productName")
    val nombre: String,
    @SerialName("supplierID")
    val idProveedor: Int,
    @SerialName("categoryID")
    val idCategoría: Int,
    @SerialName("quantityPerUnit")
    val cantidadPorUnidad: String,
    @SerialName("unitPrice")
    val precioUnidad: Double,
    @SerialName("unitsInStock")
    val unidadesEnStock: Int,
    @SerialName("unitsOnOrder")
    val unidadesEnPedido: Int,
    @SerialName("reorderLevel")
    val nivelDeReabastecimiento: Int,
    @SerialName("discontinued")
    val descontinuado: Int,
)
