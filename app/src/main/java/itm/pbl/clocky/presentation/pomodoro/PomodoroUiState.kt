package itm.pbl.clocky.presentation.pomodoro

data class PomodoroUiState(
    val minutes: String = "00",
    val seconds: String = "00",
    val totalTime: Long = 0L,
    val timeLeft: Long = 0L,
    val isPaused: Boolean = true
)