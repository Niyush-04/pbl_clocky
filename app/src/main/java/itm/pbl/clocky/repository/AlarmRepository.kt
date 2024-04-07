package itm.pbl.clocky.repository

import itm.pbl.clocky.data.Alarm
import itm.pbl.clocky.data.AlarmDao
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {
    val alarmItems = alarmDao.getAlarmsItems()

    suspend fun insert(alarm: Alarm) = alarmDao.insert(alarm)

    suspend fun update(alarm: Alarm) = alarmDao.update(alarm)

    suspend fun delete(alarm: Alarm) = alarmDao.delete(alarm)

    suspend fun getAlarmItem(alarmId: Int) = alarmDao.getAlarmItem(alarmId)
}