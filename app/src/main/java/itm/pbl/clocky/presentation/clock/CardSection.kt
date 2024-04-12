package itm.pbl.clocky.presentation.clock

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.data.clock.cards
import kotlinx.coroutines.delay
import java.time.LocalTime


@Composable
fun CardSection() {

    var currentHour by remember { mutableIntStateOf(0) }
    var currentMinute by remember { mutableIntStateOf(0) }
    var currentSecond by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = LocalTime.now()
            currentHour = currentTime.hour
            currentMinute = currentTime.minute
            currentSecond = currentTime.second
            delay(1000)
        }
    }

    Time.second = currentSecond

    LaunchedEffect(Unit) {
        Time.hour = currentHour
        Time.minute = currentMinute
    }

    LazyRow {
        items(cards.size) { index ->
            CardItem(index, currentHour,currentMinute) {
                updateHour, updateMinute ->
                Time.hour = updateHour
                Time.minute = updateMinute
            }
        }
    }
}


@Composable
fun CardItem(
    index: Int,
    currentHour: Int,
    currentMinute: Int,
    onTimeUpdate: (Int, Int) -> Unit
) {
    val card = cards[index]

    var hour = currentHour + card.offsetHours
    var minute = currentMinute + card.offsetMinutes

    var currentHour1 by remember { mutableIntStateOf(0) }
    var currentMinute1 by remember { mutableIntStateOf(0) }
    var amOrPm by remember { mutableStateOf("")}

    //minute
    if (minute >= 60) {
        hour += minute / 60
        minute %= 60
    } else if (minute < 0) {
        hour += (minute - 59) / 60
        minute = 60 + (minute % 60)
    }
    //hour

    if (hour >= 24) {
        hour %= 24
    } else if (hour < 0) {
        hour = 24 + (hour % 24)
    }

    amOrPm = if (hour>12) "PM" else "AM"
    val displayHour = if (hour == 0 || hour == 12) 12 else hour % 12

    currentHour1 = displayHour
    currentMinute1 = minute

    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = if (index == cards.size - 1) 16.dp else 0.dp,
                bottom = 16.dp,
                top = 16.dp
            )
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .width(250.dp)
                .height(200.dp)
                .clickable {
                    onTimeUpdate(currentHour1, currentMinute1)
                }
                .padding(top = 20.dp, bottom = 30.dp, start = 16.dp, end = 16.dp)

        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = card.location,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 25.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${card.gmt} HRS | GMT",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        modifier = Modifier.fillMaxHeight(0.5f),
                        alignment = Alignment.BottomStart,
                        painter = painterResource(id = card.icon),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.5f
                            )
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "%02d:%02d".format(currentHour1,currentMinute1),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 40.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.rotate(-90f),
                        text = amOrPm,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


