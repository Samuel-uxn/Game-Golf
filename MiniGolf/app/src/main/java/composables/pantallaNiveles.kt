package composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
