package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.games.minigolf.PantallaJuego

import logic.GameState
import logic.posicionesParaNivel
import logic.reiniciarHoyo


@Composable
fun MiniGolfApp() {

    var pantalla by remember { mutableStateOf("inicio") }
    var nivelSeleccionado by remember { mutableIntStateOf(1) }

    var gameState by remember {
        mutableStateOf(
            GameState(
                hoyoActual = 1,
                par = 3,
                golpes = 0,
                posicionBola = Offset(200f, 400f),
                posicionHoyo = Offset(200f, 100f),
                hoyoCompletado = false
            )
        )
    }

    when (pantalla) {

        "inicio" -> {
            PantallaInicio(onJugar = { pantalla = "niveles" })
        }

        "niveles" -> {
            PantallaNiveles(
                onNivelSeleccionado = { nivel ->
                    nivelSeleccionado = nivel
                    val par = when (nivel) { 1 -> 3; 2 -> 4; else -> 5 }
                    val (posBola, posHoyo) = posicionesParaNivel(nivel)
                    gameState = GameState(
                        hoyoActual = nivel,
                        par = par,
                        golpes = 0,
                        posicionBola = posBola,
                        posicionHoyo = posHoyo,
                        hoyoCompletado = false
                    )
                    pantalla = "juego"
                },
                onVolver = { pantalla = "inicio" }
            )
        }
        "resultado" -> {
            PantallaResultado(
                gameState = gameState,
                onSiguienteHoyo = {
                    val siguienteNivel = (gameState.hoyoActual % 3) + 1
                    val par = when (siguienteNivel) { 1 -> 3; 2 -> 4; else -> 5 }
                    val (posBola, posHoyo) = posicionesParaNivel(siguienteNivel)
                    gameState = GameState(
                        hoyoActual = siguienteNivel,
                        par = par,
                        golpes = 0,
                        posicionBola = posBola,
                        posicionHoyo = posHoyo,
                        hoyoCompletado = false
                    )
                    pantalla = "juego"
                },
                onVolverMenu = {
                    pantalla = "niveles"
                }
            )
        }
        "juego" -> {
            LaunchedEffect(gameState.hoyoCompletado) {
                if (gameState.hoyoCompletado) {
                    pantalla = "resultado"
                }
            }

            PantallaJuego(
                gameState = gameState,
                onGameStateChange = { nuevoEstado -> gameState = nuevoEstado },
                onReiniciar = {
                    val (posBola, _) = posicionesParaNivel(gameState.hoyoActual)
                    gameState = reiniciarHoyo(gameState, posBola)
                },
                onVolver = { pantalla = "niveles" }
            )
        }
    }
}
