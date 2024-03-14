package itm.pbl.clocky.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.util.CustomCircularProgressIndicator
import itm.pbl.clocky.util.vibrateDevice
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen() {
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
        val timing = 30 * 60
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

    // reset timer onDestroy screen
    DisposableEffect(Unit) {
        onDispose {
            totalTime = 0L
            timeLeft = 0L
        }
    }

    val calcProgress = ((timeLeft.toFloat() / totalTime.toFloat()) * 100f)
    val timerProgress = 100f - calcProgress

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Pomodoro",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },

    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize(0.5f),
            contentAlignment = Alignment.Center
        ) {
            // CustomCircularProgressBar(calcProgress)
            CustomCircularProgressIndicator(timerProgress)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isTimeCompleted) "Completed" else "${timeLeft / 60}:${timeLeft % 60}",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
        Text(
            text = if (timeLeft > totalTime - 25*60) "Focus" else "Enjoy",
            fontFamily = FontFamily.SansSerif,
            fontSize = 30.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
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
                containerColor = Color.LightGray,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PomodoroScreenPreview() {
    PomodoroScreen()
}
