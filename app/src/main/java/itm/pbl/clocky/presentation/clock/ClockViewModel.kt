package itm.pbl.clocky.presentation.clock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClockViewModel : ViewModel() {
    private val _hour = MutableLiveData(0)
    val hour: LiveData<Int> = _hour

    fun onHourChange(newHour: Int) {
        _hour.value = newHour
    }

    private val _minute = MutableLiveData(0)
    val minute: LiveData<Int> = _minute

    fun onMinuteChange(newMinute: Int) {
        _minute.value = newMinute
    }

    private val _second = MutableLiveData(0)
    val second: LiveData<Int> = _second

    fun onSecondChange(newSecond: Int) {
        _second.value = newSecond
    }
}
