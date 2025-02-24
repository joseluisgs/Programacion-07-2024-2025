package dev.joseluisgs

import java.util.*

fun main() {
    // Crea una instancia de `Properties` para manejar las propiedades del archivo.
    val properties = Properties()
    
    // Utiliza el cargador de clases para obtener el recurso desde la carpeta `resources`.
    val propertiesStream = {}.javaClass.classLoader.getResourceAsStream("config.properties")
    
    if (propertiesStream != null) {
        // Usa el flujo de entrada para cargar las propiedades.
        propertiesStream.use { inputStream ->
            properties.load(inputStream)
        }
        
        // Accede a las propiedades y proporciona un valor por defecto si la clave no existe.
        val username = properties.getProperty("username", "defaultUsername")
        val password = properties.getProperty("password", "defaultPassword")
        val url = properties.getProperty("url", "http://default.url")
        
        
        // Imprime los valores de las propiedades. Si alguna propiedad no se encuentra,
        // se imprime su valor por defecto.
        println("Username: $username")
        println("Password: $password")
        println("URL: $url")
    } else {
        println("El archivo de propiedades no se encontró.")
    }
}