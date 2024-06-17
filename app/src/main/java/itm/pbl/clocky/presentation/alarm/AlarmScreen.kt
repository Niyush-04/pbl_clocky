package itm.pbl.clocky.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.AlarmAdd
import androidx.compose.material.icons.rounded.AlarmOff
import androidx.compose.material.icons.rounded.AlarmOn
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import itm.pbl.clocky.alarmManager.AlarmReceiver
import itm.pbl.clocky.alarmManager.setAlarm
import java.time.LocalTime
import java.util.Calendar


@Composable
fun AlarmScreen(
    activity: AppCompatActivity,
    alarmViewModel: AlarmViewModel1,
    regBoolViewModel: ABoolViewModel,
    state: AlarmState,
    onEvent: (AlarmEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AlarmAnimation()
        }
        Spacer(modifier = Modifier.height(10.dp))

        var showBotSheetToEdit by rememberSaveable { mutableStateOf(false) }

        if (showBotSheetToEdit) {
            BotSheetContent(
                onDismiss = {
                    showBotSheetToEdit = false
                },
                activity = activity,
                alarmViewModel = alarmViewModel,
                regBoolViewModel = regBoolViewModel,
                onEvent = onEvent,
                state = state
            )
        }

        HorizontalDivider()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.size(70.dp),
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        showBotSheetToEdit = true
                        state.hour.value = ""
                        state.minute.value = ""
                        state.title.value = ""
                    }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.6f),
                        imageVector = Icons.Rounded.AlarmAdd,
                        contentDescription = "Add new Alarm",
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }, floatingActionButtonPosition = FabPosition.Center
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
                        state = state, index = index, onEvent = onEvent
                    )
                }

            }
        }
    }
}

@Composable
fun AlarmItem(
    state: AlarmState, index: Int, onEvent: (AlarmEvent) -> Unit
) {
    var activeState by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (activeState) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primaryContainer.copy(
                    alpha = 0.5f
                )
            )
            .padding(12.dp)
            .alpha(if (activeState) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {
            activeState = !activeState
        }) {

            Icon(
                imageVector = if (activeState) Icons.Rounded.AlarmOn else Icons.Rounded.AlarmOff,
                contentDescription = "Delete Alarm",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.alarms[index].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "${state.alarms[index].hour}:${state.alarms[index].minute}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        IconButton(onClick = {
            onEvent(AlarmEvent.DeleteAlarm(state.alarms[index]))

        }) {
            Icon(
                imageVector = Icons.Rounded.DeleteOutline,
                contentDescription = "Delete Alarm",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotSheetContent(
    activity: AppCompatActivity,
    alarmViewModel: AlarmViewModel1,
    regBoolViewModel: ABoolViewModel,
    onDismiss: () -> Unit,
    onEvent: (AlarmEvent) -> Unit,
    state: AlarmState
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.85f),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val currentTime = Calendar.getInstance()
            var regAlarmTime by remember { alarmViewModel::regAlarmTime }
            var regAlarmSet by remember { regBoolViewModel::regAlarmSet }

            val timePickerState = remember {
                TimePickerState(
                    is24Hour = true,
                    initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
                    initialMinute = currentTime.get(Calendar.MINUTE)
                )

            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp),
                value = state.title.value, onValueChange = {
                    state.title.value = it
                },
                placeholder = {
                    Text(text = "Title")
                },
                singleLine = true,
                shape = CircleShape,
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                TimePicker(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.Center),
                    state = timePickerState
                )
            }

            Button(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(0.9f),
                onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    state.hour.value = "%02d".format(hour)
                    state.minute.value = "%02d".format(minute)

                    regAlarmTime = "%02d:%02d".format(hour, minute)
                    regAlarmSet = !regAlarmSet
                    setAlarm(activity, hour, minute)
                    onEvent(
                        AlarmEvent.SaveAlarm(
                            hour = state.hour.value,
                            minute = state.minute.value,
                            title = state.title.value
                        )
                    )
                    onDismiss() //todo
                }

            ) {
                Text("Set Alarm")
            }
        }
    }
}


// View Model to store the user selected regular alarm time.
class AlarmViewModel1 : ViewModel() {
    var regAlarmTime: String by mutableStateOf(LocalTime.NOON.toString())
}

class ABoolViewModel : ViewModel() {
    var regAlarmSet: Boolean by mutableStateOf(false)
}

//Cancel alarm
fun cancelAlarm(context: Context) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("NotificationMessage", "Test")
    }

    // Create pending intent with same name and requestCode to cancel.
    val alarmPendingintent = PendingIntent.getBroadcast(
        context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(alarmPendingintent)
}