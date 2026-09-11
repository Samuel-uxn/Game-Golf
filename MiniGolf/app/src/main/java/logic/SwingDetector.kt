package logic

data class SwingResult(
    val fuerza: Float,
    val direccionRad: Float
)

class SwingDetector(
    private val umbralGolpe: Float = 15f,
    private val cooldownMs: Long = 800L
) {
    private var ultimoGolpeTimestamp = 0L
    private var anguloApuntado = 0f

    fun actualizarApuntado(ax: Float, ay: Float) {
        anguloApuntado = calcDireccion(ax, ay)
    }

    fun procesarLectura(ax: Float, ay: Float, az: Float, timestampMs: Long): SwingResult? {
        val magnitud = calcMagnitud(ax, ay, az)

        if (magnitud < umbralGolpe) return null
        if (timestampMs - ultimoGolpeTimestamp < cooldownMs) return null

        ultimoGolpeTimestamp = timestampMs

        return SwingResult(
            fuerza = magnitud,
            direccionRad = anguloApuntado
        )
    }
}
