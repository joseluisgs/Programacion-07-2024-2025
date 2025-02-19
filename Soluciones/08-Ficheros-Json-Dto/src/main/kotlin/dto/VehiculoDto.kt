package dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable // Necesario para serializar y deserializar
data class VehiculoDto(
    val matricula: String,
    val marca: String,
    val modelo: String,
    val color: String,
    val precio: Double,
    val fechaMatriculacion: String,
    val vendido: Boolean? = null,
    @SerialName("createdAt") // Solo si el nombre del campo es diferente
    val createdAt: String,
    val updatedAt: String,
    
    )