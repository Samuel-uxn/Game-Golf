package com.games.minigolf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.minigolf.ui.theme.MiniGolfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniGolfTheme {
                MiniGolfApp()
            }
        }
    }
}

data class GameState(
    val hoyoActual: Int,        // en qué hoyo va (1, 2, 3)
    val par: Int,               // el par de ese hoyo
    val golpes: Int,            // cuántos golpes lleva
    val posicionBola: Offset,   // dónde está la bola en pantalla (x, y)
    val posicionHoyo: Offset,   // dónde está el hoyo en pantalla (x, y)
    val hoyoCompletado: Boolean // si ya metió la bola
)
val gameStateFalso = GameState(
    hoyoActual = 1,
    par = 3,
    golpes = 0,
    posicionBola = Offset(200f, 400f),
    posicionHoyo = Offset(200f, 100f),
    hoyoCompletado = false
)


@Composable
fun MiniGolfApp() {

    var pantalla by remember {
        mutableStateOf("inicio")
    }


    var nivelSeleccionado by remember { // Nivel seleccionado
        mutableIntStateOf(1)
    }


    when (pantalla) {

        "inicio" -> {
            PantallaInicio(
                onJugar = {
                    pantalla = "niveles"
                }
            )
        }

        "niveles" -> {
            PantallaNiveles(
                onNivelSeleccionado = { nivel ->
                    nivelSeleccionado = nivel
                    pantalla = "juego"
                },
                onVolver = {
                    pantalla = "inicio"
                }
            )
        }

        "juego" -> {
            PantallaJuego(
                nivel = nivelSeleccionado,
                onVolver = {
                    pantalla = "niveles"
                }
            )
        }
    }
}

@Composable //pantalla de inicio
fun PantallaInicio(
    onJugar: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF2E7D32)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "MINI GOLF",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "¡Juega usando tu celular como palo de golf!",
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = onJugar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "JUGAR",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun PantallaNiveles(
    onNivelSeleccionado: (Int) -> Unit,
    onVolver: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE8F5E9)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "SELECCIONA UN NIVEL",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(35.dp))


            // NIVEL 1
            Button(
                onClick = {
                    onNivelSeleccionado(1)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Nivel 1",
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Par 3"
                    )
                }
            }


            Spacer(modifier = Modifier.height(20.dp))


            // NIVEL 2
            Button(
                onClick = {
                    onNivelSeleccionado(2)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Nivel 2",
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Par 4"
                    )
                }
            }


            Spacer(modifier = Modifier.height(20.dp))


            // NIVEL 3
            Button(
                onClick = {
                    onNivelSeleccionado(3)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Nivel 3",
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Par 5"
                    )
                }
            }


            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = onVolver
            ) {
                Text("Volver")
            }
        }
    }
}

@Composable  //pantalla de juegoo
fun PantallaJuego(
    nivel: Int,
    onVolver: () -> Unit
) {

    // Cada nivel tiene un par diferente.
    val par = when (nivel) {
        1 -> 3
        2 -> 4
        else -> 5
    }

    var golpes by remember {
        mutableIntStateOf(0)
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

            // INFORMACIÓN DEL NIVEL

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Hoyo $nivel",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Par $par",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(15.dp))


            // CONTADOR

            Text(
                text = "Golpes: $golpes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(15.dp))


            // CAMPO DE GOLF

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

                    // Hoyo
                    drawCircle(
                        color = Color.Black,
                        radius = 30f,
                        center = Offset(
                            size.width / 2,
                            150f
                        )
                    )

                    // Pelota
                    drawCircle(
                        color = Color.White,
                        radius = 18f,
                        center = Offset(
                            size.width / 2,
                            size.height - 150f
                        )
                    )
                }
            }


            Spacer(modifier = Modifier.height(15.dp))


            // INSTRUCCIONES

            Text(
                text = "Mueve el celular como si fuera un palo de golf para golpear la pelota.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.height(15.dp))


            // BOTONES


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        golpes = 0
                    }
                ) {
                    Text("Reiniciar")
                }

                Button(
                    onClick = onVolver
                ) {
                    Text("Niveles")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {

    MiniGolfTheme {
        PantallaInicio(
            onJugar = {}
        )
    }
}