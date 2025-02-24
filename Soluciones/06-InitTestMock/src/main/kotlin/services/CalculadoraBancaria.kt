package dev.joseluisgs.services

class CalculadoraBancaria(
    private val calculadora: Calculadora
) {

    fun calcularInteresSimple(capital: Double, tasaInteres: Double, tiempo: Double): Double {
        return calculadora.multiplicar(calculadora.multiplicar(capital, tasaInteres), tiempo)
    }

    fun calcularInteresCompuesto(capital: Double, tasaInteres: Double, tiempo: Double): Double {
        val base = calculadora.sumar(1.0, tasaInteres)
        val exponente = calculadora.multiplicar(tiempo, 1.0) // Simplemente para ilustrar el uso
        return calculadora.multiplicar(capital, Math.pow(base, exponente))
    }

    fun calcularCuotaHipoteca(capital: Double, tasaInteresAnual: Double, numeroCuotas: Int): Double {
        val tasaInteresMensual = calculadora.dividir(tasaInteresAnual, 12.0)
        val factor = Math.pow(calculadora.sumar(1.0, tasaInteresMensual), numeroCuotas.toDouble())
        val numerador = calculadora.multiplicar(capital, tasaInteresMensual)
        val denominador = calculadora.restar(factor, 1.0)
        return calculadora.dividir(numerador, denominador)
    }
}
