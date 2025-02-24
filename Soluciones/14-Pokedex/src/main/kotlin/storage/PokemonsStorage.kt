package dev.joseluisgs.storage

import dev.joseluisgs.mappers.toModel
import dev.joseluisgs.models.Pokemon
import dto.PokedexDto
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging

class PokemonsStorage {
    private val logger = logging()
    
    init {
        logger.debug { "PokemonsStorage created" }
    }
    
    fun loadPokemons(): List<Pokemon> {
        logger.debug { "Loading Pokemons" }
        // Desde resources:  object {}.javaClass.classLoader.getResource("pokemon.json")
        // // Cuidado con la barra de directorios: /data/pokemons.json
        val pokedex = {}.javaClass.getResource("/data/pokemons.json") ?: throw RuntimeException("File not found")
        val pokedexString = pokedex.readText()
        val jsonReader = Json { }
        val pokedexDto = jsonReader.decodeFromString<PokedexDto>(pokedexString)
        logger.debug { "Loaded ${pokedexDto.pokemons.size} Pokemons" }
        return pokedexDto.pokemons.map { it.toModel() }
    }
}