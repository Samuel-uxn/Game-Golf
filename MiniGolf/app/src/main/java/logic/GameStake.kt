package logic

import androidx.compose.ui.geometry.Offset

data class GameState(
    val hoyoActual: Int = 1,
    val par: Int = 3,
    val golpes: Int = 0,
    val posicionBola: Offset,
    val posicionHoyo: Offset,
    val hoyoCompletado: Boolean = false
)

fun registrarGolpe(estado: GameState, nuevaPosicionBola: Offset): GameState {
    val completado = llegoAlHoyo(nuevaPosicionBola, estado.posicionHoyo)

    return estado.copy(
        golpes = estado.golpes + 1,
        posicionBola = nuevaPosicionBola,
        hoyoCompletado = completado
    )
}

fun reiniciarHoyo(estado: GameState, posicionInicialBola: Offset): GameState {
    return estado.copy(
        golpes = 0,
        posicionBola = posicionInicialBola,
        hoyoCompletado = false
    )
}