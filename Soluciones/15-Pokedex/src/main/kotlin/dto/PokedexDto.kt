package dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokedexDto(
    @SerialName("pokemon")
    val pokemons: List<PokemonDto>
)