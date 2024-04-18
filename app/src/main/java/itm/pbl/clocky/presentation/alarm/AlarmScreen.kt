package itm.pbl.clocky.presentation.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import itm.pbl.clocky.presentation.Routes

@Composable
fun AlarmScreen(
    state: AlarmState,
    navController: NavController,
    onEvent: (AlarmEvent) -> Unit
) {
    Column(verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(250.dp,250.dp),
            imageVector = Icons.Rounded.Alarm,

            tint = MaterialTheme.colorScheme.primaryContainer,
            contentDescription = "Alarm Icon"
        )
        Divider()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.size(100.dp,50.dp),
                    onClick = {
                    state.hour.value = ""
                    state.minute.value = ""
                    state.title.value = ""
                    navController.navigate(Routes.CREATE_ALARM_SCREEN)
                }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new Alarm")
                }
            }
        ) { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.alarms.size) { index ->
                    AlarmItem(
                        state = state,
                        index = index,
                        onEvent = onEvent
                    )
                }

            }
        }
    }
}

@Composable
fun AlarmItem(
    state: AlarmState,
    index: Int,
    onEvent: (AlarmEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = state.alarms[index].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${state.alarms[index].hour}:${state.alarms[index].minute}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        IconButton(onClick = { onEvent(AlarmEvent.DeleteAlarm(state.alarms[index]))

        }
        ) {
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete Alarm",
            modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}