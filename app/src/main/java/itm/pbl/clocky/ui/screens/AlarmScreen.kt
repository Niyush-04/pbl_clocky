package itm.pbl.clocky.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = Routes.ALARM_SCREEN)
        AnalogClock()
    }
}

@Composable
fun AnalogClock() {
    Box(modifier = Modifier.fillMaxSize()){
        Canvas(modifier = Modifier.fillMaxSize()) {

            val radius = size.width * .4f
            drawCircle(
                color = Color.Black,
                style = Stroke(width = radius*0.5f),
                radius = radius,
                center = size.center
            )
        }
    }
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AnalogClockPrev() {
    AnalogClock()
    
}