package itm.pbl.clocky.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomCircularProgressIndicator(
    initialValue: Float,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.onPrimary.copy(0.50f),
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float = 300f,
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter = Offset(width / 2f, height / 2f)

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f), secondaryColor.copy(0.15f)
                    )
                ), radius = circleRadius, center = circleCenter
            )
            drawCircle(
                style = Stroke(
                    width = circleThickness
                ), color = secondaryColor, radius = circleRadius, center = circleCenter
            )
            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f / maxValue) * initialValue,
                style = Stroke(
                    width = circleThickness, cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f, height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f) / 2f, (height - circleRadius * 2f) / 2f
                )
            )

            val outerRadius = circleRadius + circleThickness / 2f
            val gap = 15f
            for (i in 1..(maxValue - minValue)) {
                val color =
                    if (i < initialValue - minValue) primaryColor else primaryColor.copy(alpha = .3f)
                val angleInDegree = i * 360f / (maxValue - minValue).toFloat()
                val angleInRad = angleInDegree * PI / 180f + PI / 2f

                val yGapAdjustment = cos(angleInDegree * PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegree * PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val end = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleThickness + circleCenter.y + yGapAdjustment).toFloat()
                )

                rotate(
                    angleInDegree, pivot = start
                ) {
                    drawLine(
                        color = color, start = start, end = end, strokeWidth = 1.dp.toPx()
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomCircularProgressIndicatorPrev() {
    CustomCircularProgressIndicator(
        initialValue = 50f, circleRadius = 300f
    )
}