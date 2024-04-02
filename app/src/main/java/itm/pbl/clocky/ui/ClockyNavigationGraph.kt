package itm.pbl.clocky.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import itm.pbl.clocky.R
import itm.pbl.clocky.ui.alarm.AlarmScreen
import itm.pbl.clocky.ui.clock.ClockScreen
import itm.pbl.clocky.ui.pomodoro.PomodoroScreen

@Composable
fun ClockyNavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navHostController,
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
    }
}

sealed class Screens(val route: String, val icon: Int) {

    data object Alarm : Screens(
        route = Routes.ALARM_SCREEN,
        icon = R.drawable.avd_clock_alarm
    )

    data object Clock : Screens(
        route = Routes.CLOCK_SCREEN,
        icon = R.drawable.avd_clock_clock
    )

    data object Pomodoro : Screens(
        route = Routes.POMODORO_SCREEN,
        icon = R.drawable.avd_clock_stopwatch
    )

}