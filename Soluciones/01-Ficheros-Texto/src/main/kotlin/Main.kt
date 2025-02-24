package dev.joseluisgs

import dev.joseluisgs.storage.FileTextStorage

fun main() {
    val storage = FileTextStorage()

    //println(text)

    val text = storage.loadToString("quijote.txt")
    text.lines().firstOrNull { it.contains("En un lugar de la Mancha", ignoreCase = true) }
        ?.let { println("Encontrado: $it") }


    val lines = storage.loadToList("quijote.txt")
    //lines.forEach { println(it) }
    lines.firstOrNull { it.contains("En un lugar de la Mancha", ignoreCase = true) }
        ?.let { println("Encontrado: $it") }


    storage.loadAndProcess("quijote.txt") {
        if (it.contains("En un lugar de la Mancha", ignoreCase = true)) {
            println("Encontrado: $it")
        }
    }

    val content = """
        |En un lugar de la Mancha
        |de cuyo nombre no quiero acordarme
        |no ha mucho tiempo que vivía
        |un hidalgo de los de lanza en astillero
    """.trimMargin()

    storage.save("quijote2.txt", content)
    // storage.save("quijote2.txt", "\n y que se llamaba Don Quijote de la Mancha")  // No se añade, has de usar el append
    storage.append("quijote2.txt", "\ny que se llamaba Don Quijote de la Mancha")

    println(storage.loadToString("quijote2.txt"))
}