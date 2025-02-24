package dev.joseluisgs.storage

import java.io.File

class FileTextStorage {
    fun loadToString(path: String): String {
        val file = File(path)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found at $path")
        }

        //return file.bufferedReader().use { it.readText() }
        return file.readText() // Solamente para archivos pequeños y que queramos cargar en memoria como una cadena
    }

    fun loadToList(path: String): List<String> {
        val file = File(path)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found at $path")
        }
        return file.readLines() // Solamente para archivos pequeños y que queramos cargar en memoria como una lista de líneas
    }

    fun loadAndProcess(path: String, action: (String) -> Unit): Unit {
        val file = File(path)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found at $path")
        }
        return file.forEachLine { action(it) } // si queremos procesar cada línea independientemente y no cargar todo en memoria
    }

    fun save(path: String, content: String): Unit {
        File(path).writeText(content) // Crea un nuevo archivo o sobreescribe si ya existe y escribe el contenido
    }

    fun append(path: String, content: String): Unit {
        File(path).appendText(content) // Añade contenido al final del archivo
    }
}