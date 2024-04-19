package itm.pbl.clocky.presentation.clock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

object Time {
    var hour: Int by mutableIntStateOf(0)
    var minute: Int by mutableIntStateOf(0)
}