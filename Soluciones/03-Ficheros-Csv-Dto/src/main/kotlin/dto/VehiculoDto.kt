package dev.joseluisgs.dto

data class VehiculoDto(
    val matricula: String,
    val marca: String,
    val modelo: String,
    val color: String,
    val precio: Double,
    val fechaMatriculacion: String,
    val vendido: Boolean = false,
    val createdAt: String,
    val updatedAt: String,
)