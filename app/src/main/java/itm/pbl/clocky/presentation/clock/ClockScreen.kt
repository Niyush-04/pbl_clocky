package itm.pbl.clocky.presentation.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ClockScreen(viewModel: ClockViewModel = viewModel()) {
    val hour by viewModel.hour.observeAsState(0)
    val minute by viewModel.minute.observeAsState(0)
    val second by viewModel.second.observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainClock(currentHour = hour, currentMinute = minute, currentSecond = second)
        TimeInText(hour = hour%12, minute = minute)
        CardSection()
    }

}
