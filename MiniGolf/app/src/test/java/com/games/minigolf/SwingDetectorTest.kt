package com.games.minigolf

import logic.SwingDetector
import logic.calcMagnitud
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import androidx.compose.ui.geometry.Offset
import logic.calcularPosicionFinal
import logic.llegoAlHoyo

class SwingDetectorTest {

    @Test
    fun `magnitud se calcula correctamente`() {
        val resultado = calcMagnitud(3f, 4f, 0f)
        assertEquals(5f, resultado, 0.01f)
    }

    @Test
    fun `golpe debil no se detecta`() {
        val detector = SwingDetector(umbralGolpe = 15f)
        val resultado = detector.procesarLectura(1f, 1f, 1f, timestampMs = 1000L)
        assertNull(resultado)
    }

    @Test
    fun `golpe fuerte si se detecta`() {
        val detector = SwingDetector(umbralGolpe = 15f)
        val resultado = detector.procesarLectura(20f, 0f, 0f, timestampMs = 1000L)
        assertNotNull(resultado)
        assertEquals(20f, resultado!!.fuerza, 0.01f)
    }

    @Test
    fun `segundo golpe inmediato se ignora por cooldown`() {
        val detector = SwingDetector(umbralGolpe = 15f, cooldownMs = 800L)

        detector.procesarLectura(20f, 0f, 0f, timestampMs = 1000L)
        val segundoGolpe = detector.procesarLectura(20f, 0f, 0f, timestampMs = 1200L)

        assertNull(segundoGolpe)
    }

    @Test
    fun `golpe despues del cooldown si se detecta`() {
        val detector = SwingDetector(umbralGolpe = 15f, cooldownMs = 800L)

        detector.procesarLectura(20f, 0f, 0f, timestampMs = 1000L)
        val tercerGolpe = detector.procesarLectura(20f, 0f, 0f, timestampMs = 2000L)

        assertNotNull(tercerGolpe)
    }
    @Test
    fun `posicion final se calcula bien en direccion horizontal`() {
        val origen = Offset(100f, 100f)
        val destino = calcularPosicionFinal(origen, fuerza = 10f, direccionRad = 0f)
        assertEquals(180f, destino.x, 0.01f) // 100 + 10*8*cos(0)=1
        assertEquals(100f, destino.y, 0.01f)
    }
    @Test
    fun `bola en el hoyo se detecta como completado`() {
        // Arrange: preparo los datos
        val posBola = Offset(200f, 200f)
        val posHoyo = Offset(200f, 200f)

        // Act: llamo la función que quiero probar
        val resultado = llegoAlHoyo(posBola, posHoyo)

        // Assert: verifico que dé lo esperado
        assertEquals(true, resultado)
    }
}