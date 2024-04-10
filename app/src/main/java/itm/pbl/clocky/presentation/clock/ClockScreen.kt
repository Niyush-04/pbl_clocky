package itm.pbl.clocky.presentation.clock

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import itm.pbl.clocky.ui.theme.from
import itm.pbl.clocky.ui.theme.tooo
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.min



@Composable
fun ClockScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = from),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainClock()
        TimeInText()
        CardSection()
    }
}

    @Composable
fun MainClock() {
    var currentHour by remember { mutableIntStateOf(0) }
    var currentMinute by remember { mutableIntStateOf(0) }
    var currentSecond by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val currentTime = LocalTime.now()
            currentHour = currentTime.hour
            currentMinute = currentTime.minute
            currentSecond = currentTime.second
        }
    }

    val hourRotation by animateFloatAsState(
        targetValue = currentHour.toFloat() % 12,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    val minuteRotation by animateFloatAsState(
        targetValue = currentMinute.toFloat() % 60f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    val secondRotation by animateFloatAsState(
        targetValue = currentSecond % 60f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    AnalogClockComponent(hour = hourRotation, minute = minuteRotation, second = secondRotation)
}

@Composable
fun AnalogClockComponent(hour: Float, minute: Float, second: Float) {
    Box(
        modifier = Modifier
            .size(250.dp, 250.dp)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape
            )
            .background(Color.White)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
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
                            0f to from,
                            0.25f to tooo,
                            1f to tooo
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
                        color = tooo,
                        radius = circleThickness / 2,
                        center = dialOffset
                    )
                    drawCircle(
                        color = Color.White.copy(1f),
                        radius = circleThickness / 3,
                        center = dialOffset
                    )
                }
            }

            rotate(houRation * 360, center) {
                drawLine(
                    color = from,
                    start = center - Offset(0f, radius * 0.6f),
                    end = center + Offset(0f, radius * 0.1f),
                    strokeWidth = 10.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            rotate(minuteRation * 360, center) {
                drawLine(
                    color = tooo,
                    start = center - Offset(0f, radius * 0.8f),
                    end = center + Offset(0f, radius * 0.1f),
                    strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            drawCircle(
                color = Color.LightGray.copy(1f),
                radius = 2.dp.toPx(),
                center = center
            )
        }
    }
}
