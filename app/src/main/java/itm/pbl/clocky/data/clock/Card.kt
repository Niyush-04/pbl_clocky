package itm.pbl.clocky.data.clock

import itm.pbl.clocky.R

data class Card(
    val Id: Int,
    val location: String,
    val offsetHours: Int,
    val offsetMinutes: Int,
    val icon: Int,
    val gmt: String
)

val cards = listOf(

    Card(
        Id = 1,
        location = "Mumbai, India",
        offsetHours = 0,
        offsetMinutes = 0,
        icon = R.drawable.mumbai_icon,
        gmt = "+5:30"
    ),
    Card(
        Id = 2,
        location = "Paris, France",
        offsetHours = -3,
        offsetMinutes = -30,
        icon = R.drawable.paris_icon,
        gmt = "+1"
    ),
    Card(
        Id = 3,
        location = "Tokyo, Japan",
        offsetHours = +3,
        offsetMinutes = +30,
        icon = R.drawable.tokyo_icon,
        gmt = "+9"
    ),
    Card(
        Id = 4,
        location = "Sydney, Australia",
        offsetHours = +4,
        offsetMinutes = +30,
        icon = R.drawable.sydney_icon,
        gmt = "+11"
    ),
    Card(
        Id = 5,
        location = "New York, USA",
        offsetHours = -9,
        offsetMinutes = -30,
        icon = R.drawable.ny_icon,
        gmt = "-5"
    )
)
