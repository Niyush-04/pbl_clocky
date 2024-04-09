package itm.pbl.clocky.presentation.alarm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import itm.pbl.clocky.R

@Composable
fun AlarmScreen(
    navigateToCreateAlarm: () -> Unit = {}
) {
    Column {
        Button(
            onClick = navigateToCreateAlarm) {
            Text(text = "Btn")
        }
    }
}
