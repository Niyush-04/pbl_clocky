package itm.pbl.clocky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import itm.pbl.clocky.data.AlarmDatabase
import itm.pbl.clocky.navigation.ClockyNavigationGraph
import itm.pbl.clocky.navigation.Routes
import itm.pbl.clocky.navigation.Screens
import itm.pbl.clocky.presentation.alarm.AlarmViewModel
import itm.pbl.clocky.ui.theme.ClockyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AlarmDatabase::class.java,
            "alarm.db"
        ).build()
    }

    private val viewModel by viewModels<AlarmViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlarmViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockyTheme {

                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BottomBar(navController = navController) },
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {

                        val state by viewModel.state.collectAsState()

                        ClockyNavigationGraph(
                            navController = navController,
                            startDestination = Routes.CLOCK_SCREEN,
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screens.Clock,
        Screens.Pomodoro,
        Screens.Alarm
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(top = 30.dp, bottom = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { Screens ->
            AddItem(
                screen = Screens,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun AddItem(
    screen: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextButton(onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }) {
            Text(
                style = TextStyle(
                    fontSize = 25.sp,
                    color = if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                ),
                text = screen.title
            )
        }

    }
}
