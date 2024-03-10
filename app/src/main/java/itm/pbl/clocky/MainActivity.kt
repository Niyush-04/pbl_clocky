package itm.pbl.clocky

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import itm.pbl.clocky.ui.screens.ClockyNavigationGraph
import itm.pbl.clocky.ui.screens.Screens
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
        Screens.Pomodoro,
        Screens.Timer
    )
    NavigationBar(modifier = Modifier
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
                icon = {
                    Image(
                        painter = painter, contentDescription = null
                    )
                },
                label = {
                    Text(text = screens.label)
                }
            )
        }
    }
}

