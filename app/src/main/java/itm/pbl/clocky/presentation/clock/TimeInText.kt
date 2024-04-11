package itm.pbl.clocky.presentation.clock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeInText(hour: Int, minute: Int) {
    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            letterSpacing = 3.sp,
            style = TextStyle(
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraLight
            ),
            text = "It's\n${numToWord(hour,minute)}"
        )
    }
}

fun numToWord(hour: Int, minute: Int): String {
    val hours = arrayOf(
        "","One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve"
    )

    val minutes = arrayOf(
        "o'clock", "five past", "ten past", "quarter past", "twenty past", "twenty-five past",
        "half past", "twenty-five to", "twenty to", "quarter to", "ten to", "five to"
    )

    return when {
        minute in 0..4 -> "${hours[hour]} ${minutes[0]}"
        minute in 5..9 -> "${minutes[1]} ${hours[hour]}"
        minute in 10 .. 14 -> "${minutes[2]} ${hours[hour]}"
        minute in 15 .. 19 -> "${minutes[3]} ${hours[hour]}"
        minute in 20 .. 24 -> "${minutes[4]} ${hours[hour]}"
        minute in 25 .. 29 -> "${minutes[5]} ${hours[hour]}"
        minute in 30 .. 34 -> "${minutes[6]} ${hours[hour]}"

        minute in 35 .. 39 -> "${minutes[7]} ${hours[if(hour==12) 1 else hour+1]}"
        minute in 40 .. 44 -> "${minutes[8]} ${hours[if(hour==12) 1 else hour+1]}"
        minute in 45 .. 49 -> "${minutes[9]} ${hours[if(hour==12) 1 else hour+1]}"
        minute in 50 .. 54 -> "${minutes[10]} ${hours[if(hour==12) 1 else hour+1]}"
        minute in 55 .. 59 -> "${minutes[11]} ${hours[if(hour==12) 1 else hour+1]}"
        else -> {"something went wrong"}
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeInTextPrev() {
    TimeInText(
        hour = 12,
        minute = 40
    )
}