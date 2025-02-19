package dev.joseluisgs.storage

import dev.joseluisgs.models.Accidente
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.time.LocalTime

class AccidentesStorage {
    private val logger = logging()
    
    init {
        logger.debug { "AccidentesStorage created" }
    }
    
    fun loadAccidentes(): List<Accidente> {
        logger.debug { "Loading Accidentes" }
        val accidentes =
            {}.javaClass.getResourceAsStream("/data/2024_Accidentalidad.csv")
                ?: throw RuntimeException("No se ha podido cargar el fichero")
        
        val result = mutableListOf<Accidente>()
        accidentes.bufferedReader().useLines { lines ->
            lines.drop(1) // saltamos la cabecera y leemos el resto de líneas
                .forEach {
                    val accidente = analizarLinea(it)
                    result.add(accidente)
                }
        }
        logger.debug { "Loaded ${result.size} accidentes" }
        return result
    }
    
    private fun parseFecha(fecha: String): LocalDate {
        // pasamos de dd/MM/YYYY a LocalDate
        val parts = fecha.split("/").map { it.toInt() }
        // LocalDate.of(2021, 10, 10) -> 2021-10-10
        return LocalDate.of(parts[2], parts[1], parts[0])
    }
    
    private fun parseHora(hora: String): LocalTime {
        // pasamos de HH:mm:ss a LocalTime
        val parts = hora.split(":").map { it.toInt() }
        // LocalTime.of(12, 0, 0) -> 12:00:00
        return LocalTime.of(parts[0], parts[1], parts[2])
    }
    
    private fun analizarLinea(linea: String): Accidente {
        val columns = linea.split(";").map { it.trim() }
        return Accidente(
            numExpediente = columns[0],
            fecha = parseFecha(columns[1]),
            hora = parseHora(columns[2]),
            calle = columns[3],
            distrito = columns[6],
            tipoAccidente = columns[7],
            estadoMeteorologico = columns[8],
            tipoVehiculo = columns[9],
            tipoPersona = columns[10],
            rangoEdad = columns[11],
            sexo = parseSexo(columns[12]),
            lesividad = columns[14],
            positivoAlcohol = parseAlcohol(columns[17]),
            positivoDrogas = parseDrogas(columns[18])
        )
    }
    
    private fun parseDrogas(drogas: String): Boolean {
        return drogas == "1"
    }
    
    private fun parseAlcohol(alcohol: String): Boolean {
        return alcohol == "S"
    }
    
    private fun parseSexo(sexo: String): Accidente.Sexo {
        return when (sexo) {
            "Hombre" -> Accidente.Sexo.HOMBRE
            "Mujer" -> Accidente.Sexo.MUJER
            else -> Accidente.Sexo.NO_ASIGNADO
        }
    }
}