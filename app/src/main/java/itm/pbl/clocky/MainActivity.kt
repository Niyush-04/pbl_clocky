package itm.pbl.clocky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import itm.pbl.clocky.ui.ClockyNavigationGraph
import itm.pbl.clocky.ui.Screens
import itm.pbl.clocky.ui.theme.ClockyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockyTheme {
                ClockyApp()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}


@Composable
fun ClockyApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNav(navController = navController)
            }
        ) {
            it
            ClockyNavigationGraph(navController, Screens.Clock.route)
        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun BottomNav(navController: NavHostController) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val list = listOf(
        Screens.Clock,
        Screens.Alarm,
        Screens.Pomodoro
    )
    NavigationBar(
        modifier = Modifier
            .padding(15.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
    ) {
        list.forEachIndexed { index, screens ->
            val state = remember { mutableStateOf(false) }
            val painter = rememberAnimatedVectorPainter(
                animatedImageVector = AnimatedImageVector.animatedVectorResource(screens.icon),
                atEnd = state.value
            )
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    navController.navigate(screens.route)
                    selectedIndex = index
                    state.value = !state.value
                },
                {
                    Icon(
                        painter = painter,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = screens.label)
                }
            )
        }
    }
}

