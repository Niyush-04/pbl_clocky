package itm.pbl.clocky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import itm.pbl.clocky.presentation.alarm.AlarmEvent
import itm.pbl.clocky.presentation.alarm.AlarmScreen
import itm.pbl.clocky.presentation.alarm.AlarmState
import itm.pbl.clocky.presentation.alarm.CreateAlarmScreen
import itm.pbl.clocky.presentation.clock.ClockScreen
import itm.pbl.clocky.presentation.pomodoro.PomodoroScreen

@Composable
fun ClockyNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    state: AlarmState,
    onEvent: (AlarmEvent) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    )
    {
        composable(route = Constants.CLOCK_SCREEN) {
            ClockScreen()
        }
        composable(route = Constants.ALARM_SCREEN) {
            AlarmScreen(
                state = state,
                navController = navController,
                onEvent = onEvent,
            )
        }
        composable(route = Constants.POMODORO_SCREEN) {
            PomodoroScreen()
        }
        composable(route = Constants.CREATE_ALARM_SCREEN) {
            CreateAlarmScreen(
                state = state,
                navController = navController,
                onEvent = onEvent
            )
        }
    }
}



sealed class Screens(val route: String, val title: String ) {

    data object Alarm : Screens(
        route = Constants.ALARM_SCREEN,
        title = "Alarm"
    )

    data object Clock : Screens(
        route = Constants.CLOCK_SCREEN,
        title = "Clock"
    )

    data object Pomodoro : Screens(
        route = Constants.POMODORO_SCREEN,
        title = "Pomodoro"
    )

}