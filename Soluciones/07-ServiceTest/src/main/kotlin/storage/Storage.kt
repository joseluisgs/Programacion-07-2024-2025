package dev.joseluisgs.storage

import java.io.File

interface Storage<T> {
    fun readFromFile(file: File): List<T>
    fun writeToFile(file: File, items: List<T>)
}