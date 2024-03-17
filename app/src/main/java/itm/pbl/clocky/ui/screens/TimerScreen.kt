package itm.pbl.clocky.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimerScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = Routes.TIMER_SCREEN
        )
    }
}



@Composable
fun CustomCircularProgressIndicator(
    timeInSec: Float,
    circleRadius: Float = 300f,
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val startAngle = 270 + timeInSec * 6f

    Box {
        Canvas(
            modifier = Modifier
                .alpha(0.8f)
                .fillMaxSize()
        ) {

            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter = Offset(width / 2f, height / 2f)

            val dialRadius = circleThickness
            val dialOffset = Offset(
                (width / 2) + (circleRadius),
                (height / 2)
            )
            rotate(startAngle, center) {
                drawArc(
                    brush = Brush.sweepGradient(
                        0f to Color.DarkGray,
                        1f to Color.Gray.copy(1f)
                    ),
                    startAngle = startAngle,
                    sweepAngle = 360f,
                    style = Stroke(
                        width = circleThickness,
                        cap = StrokeCap.Round
                    ),
                    useCenter = false,
                    size = Size(
                        width = circleRadius * 2f,
                        height = circleRadius * 2f
                    ),
                    topLeft = Offset(
                        (width - circleRadius * 2f) / 2f,
                        (height - circleRadius * 2f) / 2f
                    )
                )
                drawCircle(
                    color = Color.Gray.copy(1f),
                    radius = dialRadius / 2,
                    center = dialOffset
                )
                drawCircle(
                    color = Color.White.copy(1f),
                    radius = dialRadius / 3,
                    center = dialOffset
                )
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomCircularProgressIndicatorPrev() {
    CustomCircularProgressIndicator(
        timeInSec = 10f, circleRadius = 300f
    )
}