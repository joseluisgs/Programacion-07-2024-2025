package dev.joseluisgs.dto


import dto.VehiculoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable // Necesario para serializar y deserializar
@SerialName("vehiculos")
data class VehiculosDto(
    val vehiculos: List<VehiculoDto>
)