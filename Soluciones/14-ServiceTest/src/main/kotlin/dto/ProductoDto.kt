package dev.joseluisgs.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("product")
data class ProductoDto(
    @SerialName("productID")
    val id: Int = 0,
    @XmlElement
    @SerialName("productName")
    val nombre: String,
    @XmlElement
    @SerialName("supplierID")
    val idProveedor: Int,
    @XmlElement
    @SerialName("categoryID")
    val idCategoría: Int,
    @XmlElement
    @SerialName("quantityPerUnit")
    val cantidadPorUnidad: String,
    @XmlElement
    @SerialName("unitPrice")
    val precioUnidad: Double,
    @XmlElement
    @SerialName("unitsInStock")
    val unidadesEnStock: Int,
    @XmlElement
    @SerialName("unitsOnOrder")
    val unidadesEnPedido: Int,
    @XmlElement
    @SerialName("reorderLevel")
    val nivelDeReabastecimiento: Int,
    @XmlElement
    @SerialName("discontinued")
    val descontinuado: Int,
) : java.io.Serializable // Necesario para serialización en JVM (datos en bytes)
