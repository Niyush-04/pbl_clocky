package itm.pbl.clocky.presentation.alarm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import itm.pbl.clocky.data.alarm.Alarm

data class AlarmState(
    val alarms: List<Alarm> = emptyList(),
    val hour: MutableState<String> = mutableStateOf(""),
    val minute: MutableState<String> = mutableStateOf(""),
    val title: MutableState<String> = mutableStateOf("")
)