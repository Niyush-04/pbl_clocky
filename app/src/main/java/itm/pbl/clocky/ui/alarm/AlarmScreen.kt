package itm.pbl.clocky.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun AlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        mainAlarm()
    }
}

@Composable
fun mainAlarm() {
    var selectedTime by remember { mutableStateOf(Calendar.getInstance()) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Set Alarm Time", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TimePicker(selectedTime = selectedTime) { newTime ->
            selectedTime = newTime
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            setAlarm(context, selectedTime)
        }) {
            Text(text = "Set Alarm")
        }
    }
}

@Composable
fun TimePicker(
    selectedTime: Calendar,
    onTimeSelected: (Calendar) -> Unit
) {
    var hour by remember { mutableStateOf(selectedTime.get(Calendar.HOUR_OF_DAY)) }
    var minute by remember { mutableStateOf(selectedTime.get(Calendar.MINUTE)) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = hour.toString(),
            onValueChange = { hour = it.toIntOrNull() ?: 0 },
            label = { Text("Hour") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { },
                onDone = { }
            ),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        TextField(
            value = minute.toString(),
            onValueChange = { minute = it.toIntOrNull() ?: 0 },
            label = { Text("Minute") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = { },
                onDone = {
                    onTimeSelected(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                    })
                }
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

fun setAlarm(context: Context, time: Calendar) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        time.timeInMillis,
        pendingIntent
    )
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Handle alarm trigger (e.g., display notification)
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentTime = sdf.format(Date())
        println("Alarm triggered at $currentTime")
    }
}
