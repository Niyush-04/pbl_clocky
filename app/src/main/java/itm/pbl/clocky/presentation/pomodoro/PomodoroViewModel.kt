package itm.pbl.clocky.presentation.pomodoro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PomodoroViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PomodoroUiState())
    val uiState: StateFlow<PomodoroUiState> = _uiState.asStateFlow()

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    fun startTimer(selectedMinute: Int) {

            _uiState.value = _uiState.value.copy(
                totalTime = selectedMinute * 60 * 1000L,
                timeLeft = selectedMinute * 60 * 1000L
            )

        coroutineScope.launch {
            while (_uiState.value.timeLeft > 0) {
                delay(1000L)
                val timeLeft = _uiState.value.timeLeft - 1000L
                val updateMinutes = String.format("%02d", (timeLeft / 1000 / 60) % 60)
                val updateSeconds = String.format("%02d", (timeLeft / 1000) % 60)
                _uiState.value = _uiState.value.copy(
                    timeLeft = timeLeft,
                    minutes = updateMinutes,
                    seconds = updateSeconds,
                    isPaused = false
                )
            }
        }
    }
    fun resetTimer() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        _uiState.value = PomodoroUiState()
    }
}