package itm.pbl.clocky.presentation.clock

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun MainClock(currentHour: Int, currentMinute: Int, currentSecond: Int) {

    val hourRotation by animateFloatAsState(
        targetValue = currentHour % 12f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    val minuteRotation by animateFloatAsState(
        targetValue = currentMinute % 60f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    val secondRotation by animateFloatAsState(
        targetValue = currentSecond % 60f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    AnalogClockComponent(hour = hourRotation, minute = minuteRotation, second = secondRotation)
}

@Preview(showBackground = true)
@Composable
fun AnalogClockComponent(hour: Float, minute: Float, second: Float) {

    val primary = MaterialTheme.colorScheme.primary
    val invPrimary = MaterialTheme.colorScheme.inversePrimary
    val onPrimary = MaterialTheme.colorScheme.onPrimary

    Box(modifier = Modifier.padding(20.dp)) {
        Box(
            modifier = Modifier
                .size(250.dp, 250.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape
                )
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val diameter = min(size.width, size.height) * 0.9f
                val radius = diameter / 2

                val width = size.width
                val height = size.height


                val secondRatio = second / 60f
                val minuteRation = minute / 60f
                val houRation = hour / 12f

                val circleThickness = width / 15f

                val dialOffset = Offset(
                    (width / 2) + (radius),
                    (height / 2)
                )

                rotate(secondRatio * 360, center) {
                    rotate(-90f, center) {
                        drawArc(
                            brush = Brush.sweepGradient(
                                0f to invPrimary,
                                0.25f to primary,
                                1f to primary
                            ),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            style = Stroke(
                                width = circleThickness,
                                cap = StrokeCap.Round
                            ),
                            useCenter = false,
                            size = Size(
                                width = radius * 2f,
                                height = radius * 2f
                            ),
                            topLeft = Offset(
                                (width - radius * 2f) / 2f,
                                (height - radius * 2f) / 2f
                            )
                        )
                        drawCircle(
                            color = primary,
                            radius = circleThickness / 2,
                            center = dialOffset
                        )
                        drawCircle(
                            color = onPrimary,
                            radius = circleThickness / 3,
                            center = dialOffset
                        )
                    }
                }

                rotate(houRation * 360, center) {
                    drawLine(
                        color = invPrimary,
                        start = center - Offset(0f, radius * 0.6f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 10.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                rotate(minuteRation * 360, center) {
                    drawLine(
                        color = primary,
                        start = center - Offset(0f, radius * 0.8f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                drawCircle(
                    color = onPrimary,
                    radius = 2.dp.toPx(),
                    center = center
                )
            }
        }
    }
}
