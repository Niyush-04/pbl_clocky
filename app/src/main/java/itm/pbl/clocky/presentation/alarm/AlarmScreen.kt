package itm.pbl.clocky.presentation.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
