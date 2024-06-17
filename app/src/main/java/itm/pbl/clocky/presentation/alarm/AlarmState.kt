package itm.pbl.clocky.presentation.alarm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import itm.pbl.clocky.data.alarm.Alarm

data class AlarmState(
    val alarms: List<Alarm> = emptyList(),
    val hour: MutableState<Int> = mutableStateOf(0),
    val minute: MutableState<Int> = mutableStateOf(0),
    val title: MutableState<String> = mutableStateOf("")
)