package itm.pbl.clocky.ui.pomodoro

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.R
import itm.pbl.clocky.ui.theme.from
import itm.pbl.clocky.ui.theme.tooo
import itm.pbl.clocky.util.CustomCircularProgressIndicator
import itm.pbl.clocky.util.vibrateDevice
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
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

    // reset timer onDestroy screen
    DisposableEffect(Unit) {
        onDispose {
            totalTime = 0L
            timeLeft = 0L
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize(0.5f),
            contentAlignment = Alignment.Center
        ) {
//            CustomCircularProgressIndicator(progressBarAnim.value)
            CustomCircularProgressIndicator(initialValue = if (progressBarAnim.value != 1f) progressBarAnim.value else 100f - calcProgress)
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
            text = if (timeLeft > totalTime - 0.5 * 60) "Focus" else "Enjoy",
            fontFamily = FontFamily.SansSerif,
            fontSize = 30.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            val state = remember {
                mutableStateOf(false)
            }
            val painter = rememberAnimatedVectorPainter(
                animatedImageVector = AnimatedImageVector.animatedVectorResource(
                    id = R.drawable.avd_playpausestop_play_to_pause
                ),
                atEnd = state.value
            )

            FloatingActionButton(
                onClick = {
                    isPaused = !isPaused
                    isReset = false
                    state.value = !state.value
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                containerColor = from,
            ) {
                Icon(
                    painter = painter,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
            FloatingActionButton(
                onClick = {
                    isReset = true
                    state.value = !state.value
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                containerColor = from,
            ) {
                Icon(
                    painter = painter,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        }
    }
}