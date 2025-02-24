package dev.joseluisgs.services

import dev.joseluisgs.models.Accidente
import dev.joseluisgs.repository.AccidentesRepository
import dev.joseluisgs.storage.AccidentesStorage
import org.lighthousegames.logging.logging

class AccidentesService(
    private val repository: AccidentesRepository = AccidentesRepository(),
    private val storage: AccidentesStorage = AccidentesStorage()
) {
    private val logger = logging()
    
    init {
        logger.debug { "AccidentesService created" }
        storage.loadAccidentes().forEach { accidente ->
            repository.save(accidente)
        }
    }
    
    fun getAll(): List<Accidente> {
        logger.debug { "Getting all accidentes" }
        return repository.getAll()
    }
    
    fun getByNumExpediente(numExpediente: String): Accidente? {
        logger.debug { "Getting accidente by numExpediente: $numExpediente" }
        return repository.getByNumExpediente(numExpediente)
    }
}