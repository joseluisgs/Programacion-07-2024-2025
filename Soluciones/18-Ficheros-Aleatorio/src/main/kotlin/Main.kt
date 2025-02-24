package dev.joseluisgs

import java.io.RandomAccessFile
import kotlin.io.path.Path

fun main() {
    
    //mostrarFicheroAleatorio()
    //modificarFicheroAleatorio()
    //mostrarFicheroAleatorio()
    leerFicheroAleatorioTexto()
    //palabras()
    //leerFicheroAleatorioTexto()
}

fun palabras() {
    val fileOrigen = Path(System.getProperty("user.dir"), "data", "texto.txt").toFile()
    val aleatorio = RandomAccessFile(fileOrigen, "rw")
    
    println("Introduce una palabra: ")
    val palabra = readlnOrNull() ?: throw Exception("Palabra no válida")
    println(palabra)
    
    aleatorio.use {
        aleatorio.seek(0) // Asegúrate de comenzar al principio del archivo.
        var cadena = aleatorio.readLine()
        var posicion = aleatorio.filePointer
        System.err.println("Posición: $posicion")
        
        val resultado = StringBuilder() // Crea un nuevo contenido para el archivo.
        
        while (cadena != null) {
            val sustituido = cadena.replace(palabra, palabra.uppercase())
            resultado.appendLine(sustituido) // Añade la línea modificada al resultado.
            
            posicion = aleatorio.filePointer
            System.err.println("Posición: $posicion")
            cadena = aleatorio.readLine()
        }
        
        aleatorio.setLength(0) // Borra el contenido actual.
        aleatorio.seek(0) // Vuelve al inicio del archivo.
        //aleatorio.write(resultado.toString().toByteArray(Charsets.UTF_8)) // Escribe el nuevo contenido.
        //aleatorio.writeBytes(resultado.toString())
        aleatorio.writeUTF(resultado.toString())
    }
}

fun modificarFicheroAleatorio() {
    
    val fileOrigen = Path(System.getProperty("user.dir"), "data", "enteros.dat").toFile()
    val aleatorio = RandomAccessFile(fileOrigen, "rw")
    // use!!!!!!!!
    aleatorio.use {
        it.seek(0)
        val longitud = it.length()
        val numEnteros = longitud / 4
        println("Numero de entradas: $numEnteros")
        // indicar el entero a modificar
        println("El entero a modificar [1-$numEnteros]: ")
        val entero = readlnOrNull()?.toIntOrNull() ?: 0
        if (entero !in 1..numEnteros) {
            println("El entero no es correcto")
            return
        }
        // posicionarse en el byte a modificar
        it.seek((entero - 1) * 4L)
        // leer el entero
        val valor = it.readInt()
        println("El valor del entero es: $valor")
        // pedir el nuevo valor
        println("Nuevo valor: ")
        val nuevoValor = readlnOrNull()?.toIntOrNull() ?: 0
        // modificar el entero
        it.seek((entero - 1) * 4L)
        it.writeInt(nuevoValor)
    }
}

fun mostrarFicheroAleatorio() {
    val fileOrigen = Path(System.getProperty("user.dir"), "data", "enteros.dat")
    val aleatorio = RandomAccessFile(fileOrigen.toFile(), "r")
    // posicionarse en el byte 0
    aleatorio.use {
        aleatorio.seek(0)
        val longitud = aleatorio.length()
        println("Longitud del fichero: $longitud")
        // leemos todos los enteros
        var entero: Int
        val numEnteros = longitud / 4
        repeat(numEnteros.toInt()) {
            entero = aleatorio.readInt()
            println("Entero: $entero - Posición: ${aleatorio.filePointer}")
        }
    }
}

fun leerFicheroAleatorioTexto() {
    val fileOrigen = Path(System.getProperty("user.dir"), "data", "texto.txt").toFile()
    val aleatorio = RandomAccessFile(fileOrigen, "r")
    var linea: String?
    aleatorio.use {
        aleatorio.seek(0)
        do {
            linea = aleatorio.readLine()
            if (linea != null) {
                println(linea)
            }
        } while (linea != null)
        
        aleatorio.seek(0)
        println()
        while (aleatorio.filePointer < aleatorio.length()) {
            linea = aleatorio.readLine() // Probar con readUTF a ver que pasa
            println(linea)
        }
        println()
        aleatorio.seek(0)
        // Leer en UTF-8 (fichero entero)
        val bytes = ByteArray(aleatorio.length().toInt())
        aleatorio.readFully(bytes)
        println(String(bytes, Charsets.UTF_8))
        println()
        
        // Y si quiero lenea a linea en UTF-8
    }
    
    aleatorio.use {
        aleatorio.seek(0)
        val charset = Charsets.UTF_8
        
        while (aleatorio.filePointer < aleatorio.length()) {
            val lineBytes = mutableListOf<Byte>()
            var byte: Int
            
            while (aleatorio.filePointer < aleatorio.length()) {
                byte = aleatorio.read()
                if (byte == -1 || byte.toChar() == '\n') break
                if (byte.toChar() != '\r') { // Ignorar retorno de carro
                    lineBytes.add(byte.toByte())
                }
            }
            
            val line = String(lineBytes.toByteArray(), charset)
            println(line)
        }
    }
}
