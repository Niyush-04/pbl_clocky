package itm.pbl.clocky.presentation.clock

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.R
import itm.pbl.clocky.data.clock.Card
import itm.pbl.clocky.ui.theme.tooo
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


val cards = listOf(

    Card(
        location = "Mumbai",
        offsetHours = 5,
        offsetMinutes = 30,
        country = "India",
        icon = R.drawable.mumbai_icon
    ),
    Card(
        location = "Paris",
        offsetHours = +2,
        offsetMinutes = 0,
        country = "France",
        icon = R.drawable.paris_icon
    ),
    Card(
        location = "Tokyo",
        offsetHours = +9,
        offsetMinutes = 0,
        country = "Japan",
        icon = R.drawable.tokyo_icon
    ),
    Card(
        location = "Sydney",
        offsetHours = +10,
        offsetMinutes = 0,
        country = "Australia",
        icon = R.drawable.sydney_icon
    ),
    Card(
        location = "New York",
        offsetHours = -4,
        offsetMinutes = 0,
        country = "USA",
        icon = R.drawable.ny_icon
    )
)

@Preview
@Composable
fun CardSection() {
    LazyRow {
        items(cards.size) { index ->
            CardItem(index)
        }
    }
}

@Composable
fun CardItem(
    index: Int
) {
    val card = cards[index]

    var currentTime by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("GMT")

            val offsetMillis = (card.offsetHours * 3600 * 1000) + (card.offsetMinutes * 60 * 1000)
            val time = Date(System.currentTimeMillis() + offsetMillis)

            currentTime = sdf.format(time)
            delay(1000)
        }
    }

    var lastItemPaddingEnd = 0.dp
    if (index == cards.size - 1) {
        lastItemPaddingEnd = 16.dp
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = lastItemPaddingEnd, bottom = 16.dp, top = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(color = tooo)
                .width(250.dp)
                .height(200.dp)
                .clickable { }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = card.location,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentTime,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 35.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = card.country,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            val painter = painterResource(id = card.icon)
            Image(
                painter = painter,
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.7f)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

        }


    }
}


