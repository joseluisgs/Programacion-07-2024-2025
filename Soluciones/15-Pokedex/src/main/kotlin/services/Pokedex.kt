package dev.joseluisgs.services

import dev.joseluisgs.models.Pokemon
import dev.joseluisgs.repository.PokemonsRepository
import dev.joseluisgs.storage.PokemonsStorage
import org.lighthousegames.logging.logging

class Pokedex(
    private val repository: PokemonsRepository = PokemonsRepository(),
    private val storage: PokemonsStorage = PokemonsStorage()
) {
    private val logger = logging()
    
    init {
        logger.debug { "Pokedex created" }
        val pokemons = storage.loadPokemons()
        pokemons.forEach { repository.save(it) }
    }
    
    fun getAll(): List<Pokemon> {
        logger.debug { "Getting all Pokemons" }
        return repository.findAll()
    }
    
    fun getById(id: Int): Pokemon? {
        logger.debug { "Getting Pokemon by id: $id" }
        return repository.findById(id)
    }
}