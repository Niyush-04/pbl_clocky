package itm.pbl.clocky.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.min

@Composable
fun ClockScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(30.dp))
        Spacer(modifier = Modifier.height(30.dp))
        MainClock()
    }
}


@Composable
fun TopBar() {
    Text(
        text = "clock",
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 25.sp,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal
    )
}


@Composable
fun MainClock() {
    var currentHour by remember { mutableStateOf(0) }
    var currentMinute by remember { mutableStateOf(0) }
    var currentSecond by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val currentTime = LocalTime.now()
            currentHour = currentTime.hour
            currentMinute = currentTime.minute
            currentSecond = currentTime.second
        }
    }
    AnalogClockComponent(hour = currentHour, minute = currentMinute, second = currentSecond)
}

@Composable
fun AnalogClockComponent(hour: Int, minute: Int, second: Int) {
    var minColor = MaterialTheme.colorScheme.onPrimary
    var hrColor = MaterialTheme.colorScheme.onPrimaryContainer
    var secColor = MaterialTheme.colorScheme.error
    Box(
        modifier = Modifier
            .fillMaxSize(0.8f)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center

        ) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize(1f)
        )
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize(0.8f)
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val diameter = min(size.width, size.height) * 0.9f
            val radius = diameter / 2

            val start = center - Offset(0f, radius)
            val end = start + Offset(0f, radius / 40f)

            repeat(12) {
                rotate(it / 12f * 360) {
                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round

                    )
                }
            }


            val secondRatio = second / 60f
            val minuteRation = minute / 60f
            val houRation = hour / 12f

            rotate(houRation * 360, center) {
                drawLine(
                    color = hrColor,
                    start = center - Offset(0f, radius * 0.4f),
                    end = center + Offset(0f, radius * 0.1f),
                    strokeWidth = 3.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            rotate(minuteRation * 360, center) {
                drawLine(
                    color = minColor,
                    start = center - Offset(0f, radius * 0.6f),
                    end = center + Offset(0f, radius * 0.1f),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            rotate(secondRatio * 360, center) {
                drawLine(
                    color = secColor,
                    start = center - Offset(0f, radius * 0.7f),
                    end = center + Offset(0f, radius * 0.1f),
                    strokeWidth = 3.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            drawCircle(
                color = secColor,
                radius = 5.dp.toPx()
            )

        }
    }
}




@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun AnalogClockComponentPrev() {
    //AnalogClockComponent(hour = 10, minute = 10, second = 30)
    TopBar()
}