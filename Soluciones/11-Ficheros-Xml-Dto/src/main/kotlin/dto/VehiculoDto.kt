package dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable// Necesario para serializar y deserializar
@SerialName("vehiculo")
data class VehiculoDto(
    @XmlElement // Solo si el nombre del campo es un elemento XML y no un atributo
    val matricula: String,
    @XmlElement
    val marca: String,
    @XmlElement
    val modelo: String,
    @XmlElement
    val color: String,
    @XmlElement
    val precio: Double,
    @XmlElement
    val fechaMatriculacion: String,
    @XmlElement
    val vendido: Boolean? = null,
    @XmlElement
    val createdAt: String,
    @XmlElement
    val updatedAt: String,
    
    )