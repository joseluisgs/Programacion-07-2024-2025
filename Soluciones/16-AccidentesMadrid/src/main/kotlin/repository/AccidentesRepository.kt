package dev.joseluisgs.repository

import dev.joseluisgs.models.Accidente
import org.lighthousegames.logging.logging

class AccidentesRepository {
    private val logger = logging()
    private val accidentes = mutableMapOf<String, Accidente>()
    
    init {
        logger.debug { "AccidentesRepository created" }
    }
    
    fun getAll(): List<Accidente> {
        logger.debug { "Getting all accidentes" }
        return accidentes.values.toList()
    }
    
    fun getByNumExpediente(numExpediente: String): Accidente? {
        logger.debug { "Getting accidente by numExpediente: $numExpediente" }
        return accidentes[numExpediente]
    }
    
    fun save(accidente: Accidente) {
        //logger.debug { "Saving accidente: ${accidente.numExpediente}" }
        accidentes[accidente.numExpediente] = accidente
    }
    
}