package composables

import androidx.compose.foundation.layout.Arrangement
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
import logic.GameState


@Composable
fun PantallaResultado(
    gameState: GameState,
    onSiguienteHoyo: () -> Unit,
    onVolverMenu: () -> Unit
) {
    val puntuacion = gameState.par - gameState.golpes

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
                text = "¡HOYO COMPLETADO!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "Hoyo: ${gameState.hoyoActual}", fontSize = 20.sp, color = Color.White)
            Text(text = "Golpes: ${gameState.golpes}", fontSize = 20.sp, color = Color.White)
            Text(text = "Par del hoyo: ${gameState.par}", fontSize = 20.sp, color = Color.White)
            Text(
                text = "Puntuación: ${if (puntuacion >= 0) "+$puntuacion" else "$puntuacion"}",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onSiguienteHoyo,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("SIGUIENTE HOYO", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onVolverMenu,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("VOLVER AL MENÚ", fontSize = 18.sp)
            }
        }
    }
}