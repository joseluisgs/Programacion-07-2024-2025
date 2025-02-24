package dev.joseluisgs

import dev.joseluisgs.mappers.toDto
import dev.joseluisgs.mappers.toModel
import dev.joseluisgs.models.Vehiculo
import dto.VehiculoDto
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate

fun main() {
    println("Hola JSON!")
    val file = File("data", "vehiculos.json") // mejor que poner la ruta completa data/vehiculos.json
    if (file.exists() && file.isFile && file.canRead()) {
        println("El fichero existe")
    } else {
        throw IllegalArgumentException("El fichero no existe o no se puede leer")
    }
    
    // Leer el fichero
    // primero creamos el parser de JSON
    val jsonReader = Json { ignoreUnknownKeys = true }
    val jsonString = file.readText()
    val vehiculosDto = jsonReader.decodeFromString<List<VehiculoDto>>(jsonString)
    
    // Convertimos los DTOs a objetos Vehiculo
    val vehiculos = vehiculosDto.map { it.toModel() }
    
    
    // Mostramos los vehículos
    vehiculos.forEach { println(it) }
    
    // Todos los vehiculos agrupados pro fabricante
    val vehiculosPorMarca = vehiculos.groupBy { it.marca }
    vehiculosPorMarca.forEach { (marca, vehiculos) ->
        println("Marca: $marca")
        vehiculos.forEach { println("\t$it") }
    }
    
    vehiculos.map { it.color }.distinct().forEach { println(it) }
    
    val newVehiculos = listOf(
        Vehiculo(
            matricula = "1234ABC",
            marca = "Seat",
            modelo = "Ibiza",
            color = Vehiculo.Color.ROJO,
            precio = 12000.0,
            fechaMatriculacion = LocalDate.now(),
            vendido = false
        ),
        Vehiculo(
            matricula = "5678DEF",
            marca = "Renault",
            modelo = "Clio",
            color = Vehiculo.Color.AZUL,
            precio = 15000.0,
            fechaMatriculacion = LocalDate.now(),
            vendido = false
        )
    )
    
    // Escribimos los nuevos vehículos al fichero, vehiculos-new.json
    val newFile = File("data", "vehiculos-new.json")
    val jsonWriter = Json { prettyPrint = true }
    val vehiculoDto = newVehiculos.map { it.toDto() }
    val jsonStringNew = jsonWriter.encodeToString<List<VehiculoDto>>(vehiculoDto)
    newFile.writeText(jsonStringNew)
    
}