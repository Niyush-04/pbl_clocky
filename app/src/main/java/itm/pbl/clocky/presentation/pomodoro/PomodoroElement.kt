package itm.pbl.clocky.presentation.pomodoro

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.util.CustomCircularProgressIndicator
import itm.pbl.clocky.util.vibrateDevice
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun PomodoroElements() {
    val context = LocalContext.current
    val currentView = LocalView.current

    var isTimeCompleted by remember { mutableStateOf(false) }
    var totalTime by remember { mutableStateOf(0L) }
    var timeLeft by remember { mutableStateOf(0L) }
    var isPaused by remember { mutableStateOf(false) }
    var isReset by remember { mutableStateOf(false) }

    // To Keep screen awake
    DisposableEffect(Unit) {
        currentView.keepScreenOn = true
        onDispose {
            currentView.keepScreenOn = false
        }
    }


    // toggle keep screen on
    LaunchedEffect(isPaused) {
        currentView.keepScreenOn = !isPaused
    }

    // timer logic
    if (totalTime == 0L) {
        val timing = 2 * 60
        totalTime = timing.toLong()
        timeLeft = totalTime
    }

    LaunchedEffect(isReset) {
        if (isReset) {
            isPaused = true
            timeLeft = totalTime
            isTimeCompleted = false
        }
    }

    LaunchedEffect(
        key1 = timeLeft, key2 = isPaused
    ) {
        while (timeLeft > 0 && !isPaused) {
            delay(1000L)
            timeLeft--
        }

        if (timeLeft == 0L) {
            isTimeCompleted = true
            vibrateDevice(context)
        }
    }


    // animation
    val progressBarAnim = remember { Animatable(100f) }
    LaunchedEffect(key1 = Unit) {
        progressBarAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000),
        )
    }

    val calcProgress = ((timeLeft.toFloat() / totalTime.toFloat()) * 100f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomCircularProgressIndicator(
            timerText = if (isTimeCompleted) "Completed" else "${timeLeft / 60}:${timeLeft % 60}",
            initialValue = if (progressBarAnim.value != 1f) progressBarAnim.value else 100f - calcProgress
        )

        Box(modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .background(MaterialTheme.colorScheme.inversePrimary, shape = RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center){
            Text(
                text = if (timeLeft > totalTime - 0.5 * 60) "Focus" else "Enjoy",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            val state = remember {
                mutableStateOf(false)
            }
            FloatingActionButton(
                onClick = {
                    isPaused = !isPaused
                    isReset = false
                    state.value = !state.value
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Icon(
                    imageVector = if (isPaused) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = null
                )
            }
            FloatingActionButton(
                onClick = {
                    isReset = true
                    state.value = !state.value
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = null
                )
            }
        }
    }
}