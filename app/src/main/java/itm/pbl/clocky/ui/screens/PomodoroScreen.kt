package itm.pbl.clocky.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.util.CustomCircularProgressBar
import itm.pbl.clocky.util.vibrateDevice
import kotlinx.coroutines.delay

@Composable
fun PomodoroScreen() {
    val context = LocalContext.current
    val currentView = LocalView.current


    var isTimeCompleted by remember { mutableStateOf(false) }
    var totalTime by remember { mutableLongStateOf(0L) }
    var timeLeft by remember { mutableLongStateOf(0L) }
    var isPaused by remember { mutableStateOf(false) }
    var isReset by remember { mutableStateOf(false) }


    // To Keep screen awake
    DisposableEffect(Unit) {
        currentView.keepScreenOn = true
        onDispose {
            currentView.keepScreenOn = false
        }
    }

    //empty progress bar animation
    val progressBarAnim = remember { Animatable(100f) }
    LaunchedEffect(key1 = Unit) {
        progressBarAnim.animateTo(
            1f,
            tween(1000)
        )
    }

    //flicker animation + toggle keep screen on
    val alphaValue = remember { Animatable(1f) }
    LaunchedEffect(isPaused) {
        currentView.keepScreenOn = !isPaused

        if (isPaused && (timeLeft != totalTime)) {
            alphaValue.animateTo(
                targetValue = 0.2f,
                animationSpec = infiniteRepeatable(
                    tween(
                        1000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else {
            alphaValue.snapTo(1f)
        }

    }

    //timer logic
    if (totalTime == 0L) {
        val timing = 0.2 * 60
        totalTime = timing.toLong()
        timeLeft = totalTime
    }
    LaunchedEffect(isReset) {
        if (isReset) {
            isPaused = true
            timeLeft = totalTime
            isTimeCompleted = false
            alphaValue.snapTo(1f)
        }
    }

    LaunchedEffect(
        key1 = timeLeft,
        key2 = isPaused
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

    //reset timer onDestroy screen
    DisposableEffect(Unit) {
        onDispose {
            totalTime = 0L
            timeLeft = 0L
        }
    }

    val calcProgress = ((timeLeft.toFloat() / totalTime.toFloat()) * 100f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier
                    .alpha(alphaValue.value),
                text = if (isTimeCompleted) {
                    "Completed"
                } else {
                    "${timeLeft / 60}:${timeLeft % 60}"
                },
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                color = Color.Black
            )
            if (!isTimeCompleted) {
                CustomCircularProgressBar(100f - calcProgress)
            }
        }

        Spacer(modifier = Modifier.height(64.dp))


        AnimatedVisibility(!isTimeCompleted) {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                FloatingActionButton(
                    onClick = {
                        isPaused = !isPaused
                        isReset = false
                    },
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(4.dp),
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.LightGray
                ) {
                    Icon(
                        imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.KeyboardArrowUp,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }

                FloatingActionButton(
                    onClick = {
                        isReset = true
                    },
                shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(4.dp),
                    contentColor = MaterialTheme.colorScheme.secondary,
                    containerColor = Color.LightGray
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PomodoroScreenPreview() {
    PomodoroScreen()
}
