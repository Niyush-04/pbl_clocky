package itm.pbl.clocky.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import itm.pbl.clocky.ui.theme.tooo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String) {


    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomTopAppBarPrev() {
    CustomTopAppBar(title = "hello")

}