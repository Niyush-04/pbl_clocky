package itm.pbl.clocky.presentation.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ClockScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainClock(currentHour = Time.hour, currentMinute = Time.minute, currentSecond = Time.second)
//        test()
        TimeInText(hour = Time.hour, minute = Time.minute)

        CardSection()
    }
}

@Composable
fun test(h: Int = Time.hour, m: Int = Time.minute, s: Int = Time.second) {
    
    Text(text = "${h}:${m}:${s}")
    
    
}
