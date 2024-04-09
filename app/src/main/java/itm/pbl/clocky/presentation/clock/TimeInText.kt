package itm.pbl.clocky.presentation.clock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeInText() {
    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            modifier = Modifier.padding(start = 25.dp),
            letterSpacing = 3.sp,
            style = TextStyle(
                fontSize = 35.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraLight
            ),
            text = "It's\nTwenty-five to three"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeInTextPrev() {
    TimeInText()
}