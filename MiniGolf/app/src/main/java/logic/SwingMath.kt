package logic

import kotlin.math.sqrt
import kotlin.math.atan2
import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin
fun calcMagnitud(ax: Float, ay:Float, az: Float): Float{
    return sqrt(ax * ax + ay * ay + az * az)
}
fun calcDireccion(ax: Float, ay: Float): Float{
    return atan2(ay, ax)
}
fun calcularPosicionFinal(
    origen: Offset,
    fuerza: Float,
    direccionRad: Float,
    factorEscala: Float = 8f
): Offset {
    val distancia = fuerza * factorEscala
    val destinoX = origen.x + distancia * cos(direccionRad)
    val destinoY = origen.y + distancia * sin(direccionRad)
    return Offset(destinoX, destinoY)
}
fun llegoAlHoyo(posBola: Offset, posHoyo: Offset, radioHoyo: Float = 50f): Boolean {
    val dx = posBola.x - posHoyo.x
    val dy = posBola.y - posHoyo.y
    val distancia = sqrt(dx * dx + dy * dy)
    return distancia <= radioHoyo
}
fun posicionesParaNivel(nivel: Int): Pair<Offset, Offset> {
    return when (nivel) {
        1 -> Offset(200f, 400f) to Offset(200f, 100f)
        2 -> Offset(100f, 500f) to Offset(300f, 80f)
        else -> Offset(300f, 550f) to Offset(150f, 60f)
    }
}