package itm.pbl.clocky.presentation.pomodoro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import itm.pbl.clocky.ui.theme.tooo

@Composable
fun PomodoroScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 5.dp, start = 15.dp, end = 15.dp, bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PomodoroElements()
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .size(90.dp,85.dp)
                    .background(tooo),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .padding(5.dp),
                    imageVector = Icons.TwoTone.AddCircle, contentDescription = null
                )

            }
        }
    }
}