package dev.joseluisgs.mappers

import dev.joseluisgs.models.Vehiculo
import dto.VehiculoDto
import java.time.LocalDate
import java.time.LocalDateTime

// En Kotlin lo más fácil es una función de extensión
fun Vehiculo.toDto() = VehiculoDto(
    matricula = matricula,
    marca = marca,
    modelo = modelo,
    precio = precio,
    color = color.name,
    fechaMatriculacion = fechaMatriculacion.toString(),
    vendido = vendido,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString(),
)

fun VehiculoDto.toModel() = Vehiculo(
    matricula = matricula,
    marca = marca,
    modelo = modelo,
    precio = precio,
    color = Vehiculo.Color.valueOf(color.uppercase()),
    fechaMatriculacion = LocalDate.parse(fechaMatriculacion),
    vendido = vendido ?: false,
    createdAt = LocalDateTime.parse(createdAt),
    updatedAt = LocalDateTime.parse(updatedAt),
)

