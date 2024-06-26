package itm.pbl.clocky.data.alarm

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Alarm::class],
    version = 1,
    exportSchema = false
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val dao: AlarmDao
}