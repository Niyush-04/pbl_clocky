package itm.pbl.clocky.presentation.clock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Time {
    var hour: Int by mutableStateOf(0)
    var minute: Int by mutableStateOf(0)
    var second: Int by mutableStateOf(0)
}