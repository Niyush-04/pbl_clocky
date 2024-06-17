package itm.pbl.clocky.presentation.alarm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import itm.pbl.clocky.data.alarm.Alarm
import itm.pbl.clocky.data.alarm.AlarmDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val dao: AlarmDao
) : ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var alarms = isSortedByDateAdded.flatMapLatest { sort ->
        if (sort) {
            dao.getAlarmItem()
        }else {
            dao.getAlarmItemDes()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(AlarmState())
    val state =
        combine(_state,isSortedByDateAdded,alarms) { state, isSortedByDateAdded, alarms ->
            state.copy(
                alarms = alarms
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AlarmState())

    fun onEvent(event: AlarmEvent) {
        when (event) {
            is AlarmEvent.DeleteAlarm -> {
                viewModelScope.launch {
                    dao.deleteAlarm(event.alarm)
                }
            }

            is AlarmEvent.SaveAlarm -> {
                val alarm = Alarm(
                    hour = state.value.hour.value,
                    minute = state.value.minute.value,
                    title = state.value.title.value,
                )
                viewModelScope.launch {
                    dao.upsertAlarm(alarm)
                }

                _state.update {
                    it.copy(
                        hour = mutableStateOf(0),
                        minute = mutableStateOf(0),
                        title = mutableStateOf("")
                    )
                }
            }
        }
    }
}