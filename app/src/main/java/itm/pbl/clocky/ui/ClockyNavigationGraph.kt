package itm.pbl.clocky.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import itm.pbl.clocky.R
import itm.pbl.clocky.ui.alarm.AlarmScreen
import itm.pbl.clocky.ui.clock.ClockScreen
import itm.pbl.clocky.ui.pomodoro.PomodoroScreen
import itm.pbl.clocky.ui.timer.TimerScreen

@Composable
fun ClockyNavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {

    NavHost(navController = navHostController,
        startDestination = startDestination,
    )
        {
            composable(Routes.CLOCK_SCREEN) {
                ClockScreen()
            }
            composable(Routes.ALARM_SCREEN) {
                AlarmScreen()
            }
            composable(Routes.POMODORO_SCREEN) {
                PomodoroScreen()
            }
            composable(Routes.TIMER_SCREEN) {
                TimerScreen()
            }
        }
}

sealed class Screens(val route: String, val label: String, val icon:Int) {

    object Alarm : Screens(
        route = Routes.ALARM_SCREEN,
        label = "Alarm",
        icon = R.drawable.avd_clock_alarm
    )

    object Clock : Screens(
        route = Routes.CLOCK_SCREEN,
        label = "Clock",
        icon = R.drawable.avd_clock_clock
    )

    object Pomodoro : Screens(
        route = Routes.POMODORO_SCREEN,
        label = "Pomodoro",
        icon = R.drawable.avd_clock_stopwatch
    )

    object Timer : Screens(
        route = Routes.TIMER_SCREEN,
        label = "Timer",
        icon = R.drawable.avd_clock_timer
    )
}