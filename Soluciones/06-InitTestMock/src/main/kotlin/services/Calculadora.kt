package dev.joseluisgs.services

class Calculadora {

    fun sumar(a: Double, b: Double): Double {
        return a + b
        // TODO()
    }

    fun restar(a: Double, b: Double): Double {
        return a - b
        // TODO()
    }

    fun multiplicar(a: Double, b: Double): Double {
        return a * b
        // TODO()
    }

    fun dividir(a: Double, b: Double): Double {
        require(b != 0.0) { "Divisor no puede ser 0" }
        //if (b == 0.0) throw IllegalArgumentException("Divisor no puede ser 0")
        return a / b

        // TODO()
    }
}