package itm.pbl.clocky.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert
    suspend fun insert(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Query("SELECT * FROM ALARM_ITEM_TABLE ORDER BY alarmId DESC")
    fun getAlarmsItems(): Flow<List<Alarm>>

    @Query("SELECT * FROM ALARM_ITEM_TABLE WHERE alarmId=:alarmId")
    suspend fun getAlarmItem(alarmId: Int): Alarm?
}