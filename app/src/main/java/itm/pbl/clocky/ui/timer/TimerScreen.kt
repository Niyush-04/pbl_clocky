package itm.pbl.clocky.ui.timer

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import itm.pbl.clocky.ui.Routes

@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,

) {
    Column(Modifier.fillMaxSize()) {

        Text(text = Routes.TIMER_SCREEN)
    }
}