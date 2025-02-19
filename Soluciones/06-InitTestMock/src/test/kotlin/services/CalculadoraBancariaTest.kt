package services

import dev.joseluisgs.services.Calculadora
import dev.joseluisgs.services.CalculadoraBancaria
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.math.pow

@ExtendWith(MockKExtension::class)
class CalculadoraBancariaTest {

    @MockK
    private lateinit var calculadoraMock: Calculadora // Mock o simulación
    // este codigo es equivalente a
    // private val calculador: Calculadora = mockk()

    @InjectMockKs
    private lateinit var calculadoraBancaria: CalculadoraBancaria // SUT, System Under Test
    // este codigo es equivalente a
    // private val calculadoraBancaria: CalculadoraBancaria = CalculadoraBancaria(calculador)


    @Test
    fun `calcularInteresSimple debe devolver el interés correcto`() {

        // Arrange
        every { calculadoraMock.multiplicar(1000.0, 0.05) } returns 50.0
        every { calculadoraMock.multiplicar(50.0, 2.0) } returns 100.0

        // Act
        val interes = calculadoraBancaria.calcularInteresSimple(1000.0, 0.05, 2.0)

        // Assert
        assertEquals(100.0, interes, 0.001, "El interés no es el esperado")

        // Opcionalmente, se puede verificar que se llamaron a los métodos correctos
        verify(exactly = 1) { calculadoraMock.multiplicar(1000.0, 0.05) }
        verify(exactly = 1) { calculadoraMock.multiplicar(50.0, 2.0) }
    }

    @Test
    fun `calcularInteresCompuesto debe devolver el importe correcto`() {

        // Arrange
        every { calculadoraMock.sumar(1.0, 0.05) } returns 1.05
        every { calculadoraMock.multiplicar(2.0, 1.0) } returns 2.0
        every { calculadoraMock.multiplicar(1000.0, 1.1025) } returns 1102.5

        // Act

        val monto = calculadoraBancaria.calcularInteresCompuesto(1000.0, 0.05, 2.0)

        // Assert
        assertEquals(1102.5, monto, 0.001, "El importe no es el esperado")

        // Opcionalmente, se puede verificar que se llamaron a los métodos correctos
        verify(exactly = 1) { calculadoraMock.sumar(1.0, 0.05) }
        verify(exactly = 1) { calculadoraMock.multiplicar(2.0, 1.0) }
        verify(exactly = 1) { calculadoraMock.multiplicar(1000.0, 1.1025) }


    }

    @Test
    fun `calcularCuotaHipoteca debe devolver la cuota correcta`() {
        // Arrange
        val capital = 200000.0
        val tasaInteresAnual = 0.05
        val numeroCuotas = 360

        every { calculadoraMock.dividir(tasaInteresAnual, 12.0) } returns 0.00416667
        every { calculadoraMock.sumar(1.0, 0.00416667) } returns 1.00416667
        every { calculadoraMock.multiplicar(capital, 0.00416667) } returns 833.3334
        every { calculadoraMock.restar(1.00416667.pow(numeroCuotas), 1.0) } returns 3.46774431
        every { calculadoraMock.dividir(833.3334, 3.46774431) } returns 240.0

        // Act
        val cuota = calculadoraBancaria.calcularCuotaHipoteca(capital, tasaInteresAnual, numeroCuotas)

        // Assert
        assertEquals(240.0, cuota, 0.001, "La cuota no es la esperada")

        // Opcionalmente, se puede verificar que se llamaron a los métodos correctos
        verify(exactly = 1) { calculadoraMock.dividir(tasaInteresAnual, 12.0) }
        verify(exactly = 1) { calculadoraMock.sumar(1.0, 0.00416667) }
        verify(exactly = 1) { calculadoraMock.multiplicar(capital, 0.00416667) }
        verify(exactly = 1) { calculadoraMock.restar(1.00416667.pow(numeroCuotas), 1.0) }
        verify(exactly = 1) { calculadoraMock.dividir(833.3334, 3.46774431) }
    }
}