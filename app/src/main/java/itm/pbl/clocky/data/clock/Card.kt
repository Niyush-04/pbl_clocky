package itm.pbl.clocky.data.clock

import itm.pbl.clocky.R

data class Card(
    val location: String,
    val offsetHours: Int,
    val offsetMinutes: Int,
    val icon: Int,
    val gmt: String
)

val cards = listOf(

    Card(
        location = "Mumbai, India",
        offsetHours = 0,
        offsetMinutes = 0,
        icon = R.drawable.mumbai_icon,
        gmt = "+5:30"
    ),
    Card(
        location = "Paris, France",
        offsetHours = -3,
        offsetMinutes = -30,
        icon = R.drawable.paris_icon,
        gmt = "+1"
    ),
    Card(
        location = "Tokyo, Japan",
        offsetHours = +3,
        offsetMinutes = +30,
        icon = R.drawable.tokyo_icon,
        gmt = "+9"
    ),
    Card(
        location = "Sydney, Australia",
        offsetHours = +4,
        offsetMinutes = +30,
        icon = R.drawable.sydney_icon,
        gmt = "+11"
    ),
    Card(
        location = "New York, USA",
        offsetHours = -9,
        offsetMinutes = -30,
        icon = R.drawable.ny_icon,
        gmt = "-5"
    )
)
