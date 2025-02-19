package dev.joseluisgs.repository

import dev.joseluisgs.models.Pokemon
import org.lighthousegames.logging.logging


class PokemonsRepository {
    private val logger = logging()
    private val pokemons = mutableMapOf<Int, Pokemon>()
    
    init {
        logger.debug { "PokemonsRepository created" }
    }
    
    fun findAll(): List<Pokemon> {
        logger.debug { "Finding all Pokemons" }
        return pokemons.values.toList()
    }
    
    fun findById(id: Int): Pokemon? {
        logger.debug { "Finding Pokemon by id: $id" }
        return pokemons[id]
    }
    
    fun save(pokemon: Pokemon) {
        logger.debug { "Saving Pokemon: ${pokemon.name}" }
        pokemons[pokemon.id] = pokemon
    }
}