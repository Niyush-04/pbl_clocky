package itm.pbl.clocky.data.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val alarmId: Int = 0,
    var hour: String,
    var minute: String,
    var title: String,
)
