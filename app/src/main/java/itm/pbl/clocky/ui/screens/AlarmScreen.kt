package itm.pbl.clocky.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Text(text = Routes.ALARM_SCREEN)
        testColor()
    }
}


@Composable
fun testColor() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.onBackground)
                .fillMaxWidth())
            {
                Text(text = "onBackground")
            }
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth())
            {
                Text(text = "background")
            }
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth())
            {
                Text(text = "Primary")
            }
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.errorContainer)
                .fillMaxWidth())
            {
                Text(text = "errorContainer")
            }
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.inversePrimary)
                .fillMaxWidth())
            {
                Text(text = "inversePrimary")
            }

        }
    }
}