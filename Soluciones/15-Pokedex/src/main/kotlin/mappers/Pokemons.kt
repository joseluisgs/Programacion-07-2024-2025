package dev.joseluisgs.mappers

import dev.joseluisgs.models.Pokemon
import dto.PokemonDto

fun PokemonDto.toModel(): Pokemon {
    return Pokemon(
        avgSpawns = avgSpawns,
        candy = candy,
        candyCount = candyCount ?: 0,
        egg = checkingEgg(egg),
        height = height.removeSuffix(" m").toDouble(),
        id = id,
        img = img,
        multipliers = multipliers,
        name = name,
        nextEvolution = nextEvolution,
        num = num,
        prevEvolution = prevEvolution,
        spawnChance = spawnChance,
        spawnTime = spawnTime,
        type = type,
        weaknesses = weaknesses,
        weight = weight.removeSuffix(" kg").toDouble()
    )
}

fun checkingEgg(egg: String): Int {
    if (egg == "Not in Eggs") return 0
    if (egg == "Omanyte Candy") return 0
    return egg.removeSuffix(" km").toInt()
}
