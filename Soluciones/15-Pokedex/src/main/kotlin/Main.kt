package dev.joseluisgs

import dev.joseluisgs.services.Pokedex

fun main() {
    println("Pokedex DAW - Kotlin Edition")
    val service = Pokedex()
    val pokemons = service.getAll()
    pokemons.forEach { println(it) }
    
    println("Pokemon con id 10")
    println(service.getById(10))
    
    println("Pokemons: ${pokemons.size}")
    
    // 10 primeros pokemons
    println("10 primeros pokemons")
    pokemons.take(10).forEach {
        println(it)
    }
    
    // pokemon mas pesado
    val pokemonMasPesado = pokemons.maxByOrNull { it.weight }
    println("Pokemon mas pesado: $pokemonMasPesado")
    
    // pokemon mas ligero
    val pokemonMasLigero = pokemons.minByOrNull { it.weight }
    println("Pokemon mas ligero: $pokemonMasLigero")
    
    // Pokemnos con mas evoluciones
    val pokemonMasEvolucionado = pokemons.maxByOrNull { it.nextEvolution?.size ?: 0 }
    println("Pokemon mas evolucionado: $pokemonMasEvolucionado")
    
    // Pokemnos con menos evoluciones
    val pokemonMenosEvolucionado = pokemons.minByOrNull { it.nextEvolution?.size ?: 0 }
    println("Pokemon menos evolucionado: $pokemonMenosEvolucionado")
    
    // Pokemnos con mas debilidades
    val pokemonMasDebil = pokemons.maxByOrNull { it.weaknesses.size }
    println("Pokemon mas debil: $pokemonMasDebil")
    
    // Pokemon electrico
    val pokemonElectric = pokemons.filter { it.type.contains("Electric") }
    println("Pokemon electricos: ${pokemonElectric.size}")
    
    // Pikachu
    val pikachu = pokemons.find { it.name == "Pikachu" }
    println("Pikachu: $pikachu")
    
    // Numero de pokemons por tipo
    val pokemonsByType = pokemons.groupBy { it.type }
    pokemonsByType.forEach { (type, pokemons) ->
        println("Tipo: $type, Pokemons: ${pokemons.size}")
    }
    
    // Agrupados por debilidades
    val pokemonsByWeaknesses = pokemons.flatMap { it.weaknesses }.distinct().associateWith { weakness ->
        pokemons.filter { it.weaknesses.contains(weakness) }
    }
    pokemonsByWeaknesses.forEach { (weakness, pokemons) ->
        println("Debilidad: $weakness, Pokemons: ${pokemons.size}")
    }
    
    // Debiliades y numero de pokemons
    val weaknessCount = pokemons
        .flatMap { it.weaknesses }.distinct()
        .groupingBy { it }.eachCount()
    weaknessCount.forEach { (weakness, count) ->
        println("Debilidad: $weakness, Pokemons: $count")
    }
    
    // Pokemons electricos que son debiles al veneno
    val electricVenomous = pokemons
        .filter { it.type.contains("Electric") && it.weaknesses.contains("Ground") }
        .map { it.name }
    println("Pokemons electricos debiles al veneno: $electricVenomous")
    
}