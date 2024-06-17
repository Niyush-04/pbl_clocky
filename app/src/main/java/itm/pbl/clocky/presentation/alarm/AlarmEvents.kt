package itm.pbl.clocky.presentation.alarm

import itm.pbl.clocky.data.alarm.Alarm

sealed interface AlarmEvent {
    data class DeleteAlarm(val alarm: Alarm): AlarmEvent

    data class SaveAlarm(
        val hour: Int,
        val minute: Int,
        val title: String
    ): AlarmEvent
}