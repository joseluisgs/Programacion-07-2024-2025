package dev.joseluisgs

import dev.joseluisgs.dto.VehiculosDto
import dev.joseluisgs.mappers.toDto
import dev.joseluisgs.mappers.toModel
import dev.joseluisgs.models.Vehiculo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File
import java.time.LocalDate

fun main() {
    println("Hola XML!")
    val file = File("data", "vehiculos.xml") // mejor que poner la ruta completa data/vehiculos.xml
    if (file.exists() && file.isFile && file.canRead()) {
        println("El fichero existe")
    } else {
        throw IllegalArgumentException("El fichero no existe o no se puede leer")
    }
    
    // Leer el fichero
    // primero creamos el parser de XML
    val xmlReader = XML { }
    val xmlString = file.readText()
    val vehiculosDto = xmlReader.decodeFromString<VehiculosDto>(xmlString)
    
    // Convertimos los DTOs a objetos Vehiculo
    val vehiculos = vehiculosDto.vehiculos.map { it.toModel() }
    
    
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
    
    // Escribimos los nuevos vehículos al fichero, vehiculos-new.xml
    val newFile = File("data", "vehiculos-new.xml")
    val xmlWriter = XML { indent = 4 }
    val vehiculoDto = newVehiculos.map { it.toDto() }
    val xmlStringNew = xmlWriter.encodeToString<VehiculosDto>(VehiculosDto(vehiculoDto))
    newFile.writeText(xmlStringNew)
    
}