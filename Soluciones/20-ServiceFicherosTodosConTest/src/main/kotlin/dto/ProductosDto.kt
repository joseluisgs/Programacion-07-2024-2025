package dev.joseluisgs.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("products")
data class ProductosDto(
    val products: List<ProductoDto>
) : java.io.Serializable
