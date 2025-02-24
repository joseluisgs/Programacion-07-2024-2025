package dev.joseluisgs

import dev.joseluisgs.dto.VehiculoDto
import dev.joseluisgs.mappers.toDto
import dev.joseluisgs.mappers.toModel
import dev.joseluisgs.models.Vehiculo
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

fun main() {
    println("Hola CSV!")
    val file = File("data", "vehiculos.csv") // mejor que poner la ruta completa data/vehiculos.csv
    if (file.exists() && file.isFile && file.canRead()) {
        println("El fichero existe")
    } else {
        throw IllegalArgumentException("El fichero no existe o no se puede leer")
    }

    // Leer el fichero
    val vehiculos = file.readLines().drop(1) // Saltamos la cabecera, si la tiene
        .map { linea -> linea.split(',') } // Dividimos cada línea en columnas
        //.map { columna -> columna.map { it.trim() } } // Limpiamos los espacios en blanco (no es obligatoria)
        .map { item ->
            println(item)
            VehiculoDto(
                matricula = item[0].trim(), // Limpiamos los espacios en blanco ya depende
                marca = item[1],
                modelo = item[2],
                color = item[3],
                precio = item[4].toDouble(),
                fechaMatriculacion = item[5],
                vendido = item[6].toBoolean(),
                createdAt = item[7],
                updatedAt = item[8]
            ).toModel()
        }//.map { it.toModel() }


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

    // Escribimos los nuevos vehículos al fichero, vehiculos-new.csv
    val newFile = File("data", "vehiculos-new.csv")
    newFile.writeText("matricula,marca,modelo,color,precio,fechaMatriculacion,vendido,createdAt,updatedAt\n")
    newVehiculos.map { it.toDto() }.forEach {
        newFile.appendText(
            "${it.matricula},${it.marca},${it.modelo},${it.color},${it.precio},${it.fechaMatriculacion},${it.vendido},${LocalDateTime.now()},${LocalDateTime.now()}\n"
        )
    }
    println("Nuevos vehículos escritos al fichero vehiculos-new.csv")

    // Mostramos los vehículos
    newFile.readLines().forEach { println(it) }

}