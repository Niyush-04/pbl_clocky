package itm.pbl.clocky.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Upsert
    suspend fun upsertAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarm ORDER BY title ASC")
    fun getAlarmItem(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarm ORDER BY title DESC")
    fun getAlarmItemDes(): Flow<List<Alarm>>
}