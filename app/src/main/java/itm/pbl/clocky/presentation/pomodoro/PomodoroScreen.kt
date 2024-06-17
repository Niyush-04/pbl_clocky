package itm.pbl.clocky.presentation.pomodoro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import itm.pbl.clocky.notification.NotificationService
import itm.pbl.clocky.util.CustomCircularProgressIndicator

@Composable
fun PomodoroScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PomodoroElements()
    }
}

@Preview(showBackground = true)
@Composable
fun PomodoroElements(pomodoroViewModel: PomodoroViewModel = viewModel()) {
    val uiState by pomodoroViewModel.uiState.collectAsState()
    val currentView = LocalView.current
    val context = LocalContext.current
    val notificationService = NotificationService(context)

    // To Keep screen awake
    DisposableEffect(Unit) {
        currentView.keepScreenOn = true
        onDispose {
            currentView.keepScreenOn = false
        }
    }

    // toggle keep screen on
    LaunchedEffect(uiState.isPaused) {
        currentView.keepScreenOn = !uiState.isPaused
    }

    // animation
    val progressBarAnim = remember { Animatable(100f) }
    LaunchedEffect(key1 = Unit) {
        progressBarAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

/*
calculating Progress by this method to
achieve a good UI transition.
TODO: will fix it later
 */
        CustomCircularProgressIndicator(
            timerText = "${uiState.minutes}:${uiState.seconds}",
            initialValue = if (progressBarAnim.value != 1f) {
                progressBarAnim.value
            } else {
                if (uiState.isPaused) 0f else (((60 - uiState.seconds.toFloat()) / 60f) * 100f)
            }
        )

        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)

        ) {
            if (uiState.isPaused) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Choose the Duration",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(
                        top = 0.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    text = buildAnnotatedString {
                        append("➜ 30min = 25min focus + 5min break\n")
                        append("➜ 60min = 50min focus + 10min break")
                    },
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal
                )
            } else {

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = if (uiState.timeLeft.toFloat() / uiState.totalTime.toFloat() > 0.16) "Focus" else "Break",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 25.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        AnimatedVisibility(
            visible = uiState.isPaused,
        )

        {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Button(
                    onClick = {
                        pomodoroViewModel.startTimer(30)
                        notificationService.showNotification("started")
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(56.dp)
                        .weight(1f)
                )
                {
                    Text(text = "30 Minute", fontSize = 16.sp)
                }

                Button(
                    onClick = {
                        pomodoroViewModel.startTimer(60)
                        notificationService.showNotification("started")
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(56.dp)
                        .weight(1f)
                )
                {
                    Text(text = "60 Minute", fontSize = 16.sp)
                }
            }
        }
        AnimatedVisibility(visible = !uiState.isPaused) {
            Button(
                onClick = {
                    pomodoroViewModel.resetTimer()
                    notificationService.showNotification("Restart")
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Restart", fontSize = 16.sp)
            }
        }
    }
}

