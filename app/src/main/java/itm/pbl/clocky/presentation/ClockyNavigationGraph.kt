package itm.pbl.clocky.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import itm.pbl.clocky.R
import itm.pbl.clocky.presentation.alarm.AlarmScreen
import itm.pbl.clocky.presentation.alarm.CreateAlarmScreen
import itm.pbl.clocky.presentation.clock.ClockScreen
import itm.pbl.clocky.presentation.pomodoro.PomodoroScreen

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
        composable(route = Routes.CLOCK_SCREEN) {
            ClockScreen()
        }
        composable(route = Routes.ALARM_SCREEN) {
            AlarmScreen(
                navigateToCreateAlarm = { navHostController.navigate(Routes.CREATE_ALARM_SCREEN)}
            )
        }
        composable(route = Routes.POMODORO_SCREEN) {
            PomodoroScreen()
        }
        composable(route = Routes.CREATE_ALARM_SCREEN) {
            CreateAlarmScreen()
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