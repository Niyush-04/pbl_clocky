package itm.pbl.clocky.presentation.alarm

import itm.pbl.clocky.data.Alarm

sealed interface AlarmEvent {
    data class DeleteAlarm(val alarm: Alarm): AlarmEvent

    data class SaveAlarm(
        val hour: String,
        val minute: String,
        val title: String
    ): AlarmEvent
}