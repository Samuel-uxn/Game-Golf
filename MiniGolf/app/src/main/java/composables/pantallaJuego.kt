package composables

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import logic.GameState
import logic.SwingDetector
import logic.calcDireccion
import logic.calcMagnitud
import logic.calcularPosicionFinal
import logic.registrarGolpe


@Composable
fun PantallaJuego(
    gameState: GameState,
    onGameStateChange: (GameState) -> Unit,
    onReiniciar: () -> Unit,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    val sensorManager = remember {
        context.getSystemService(SensorManager::class.java)
    }
    var ultimaMagnitud by remember { mutableFloatStateOf(0f) }
    var ultimoAngulo by remember { mutableFloatStateOf(0f) }
    val swingDetector = remember { SwingDetector() }
    val estadoActual = rememberUpdatedState(gameState)


    DisposableEffect(sensorManager) {

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val ax = event.values[0]
                    val ay = event.values[1]
                    val az = event.values[2]

                    swingDetector.actualizarApuntado(ax, ay)

                    val resultado = swingDetector.procesarLectura(
                        ax, ay, az,
                        timestampMs = System.currentTimeMillis()
                    )

                    ultimaMagnitud = calcMagnitud(ax, ay, az)
                    ultimoAngulo = Math.toDegrees(calcDireccion(ax, ay).toDouble()).toFloat()
                    val estado = estadoActual.value

                    if (resultado != null && !estado.hoyoCompletado) {
                        val nuevaPosicion = calcularPosicionFinal(
                            origen = estado.posicionBola,
                            fuerza = resultado.fuerza,
                            direccionRad = resultado.direccionRad
                        )
                        onGameStateChange(registrarGolpe(estado, nuevaPosicion))
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE8F5E9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hoyo ${gameState.hoyoActual}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Par ${gameState.par}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Golpes: ${gameState.golpes}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Debug: mag=%.1f, ang=%.0f°".format(ultimaMagnitud, ultimoAngulo),
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF66BB6A)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawCircle(
                        color = Color.Black,
                        radius = 50f,
                        center = gameState.posicionHoyo
                    )

                    drawCircle(
                        color = Color.White,
                        radius = 18f,
                        center = gameState.posicionBola
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Mueve el celular como si fuera un palo de golf para golpear la pelota.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onReiniciar) {
                    Text("Reiniciar")
                }
                Button(onClick = onVolver) {
                    Text("Niveles")
                }
            }

            if (gameState.hoyoCompletado) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "¡HOYO COMPLETADO!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
            }
        }
    }
}