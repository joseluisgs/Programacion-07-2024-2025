package dev.joseluisgs.models


import dto.NextEvolution
import dto.PrevEvolution

data class Pokemon(
    val avgSpawns: Double,
    val candy: String,
    val candyCount: Int,
    val egg: Int,
    val height: Double,
    val id: Int,
    val img: String,
    val multipliers: List<Double>? = null,
    val name: String,
    val nextEvolution: List<NextEvolution>? = null,
    val num: String,
    val prevEvolution: List<PrevEvolution>? = null,
    val spawnChance: Double,
    val spawnTime: String,
    val type: List<String>,
    val weaknesses: List<String>,
    val weight: Double
)