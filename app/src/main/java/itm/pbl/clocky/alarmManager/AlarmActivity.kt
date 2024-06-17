package itm.pbl.clocky.alarmManager

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import itm.pbl.clocky.MainActivity
import java.util.Calendar

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmContent(this)
        }
    }
}


@Composable
fun AlarmContent(activity: ComponentActivity) {

    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .padding(horizontal = 16.dp)
        , contentAlignment = Alignment.TopCenter) {
        Column(
            horizontalAlignment =
            Alignment.CenterHorizontally
        ) {
            Text(text = "Alarm Activity")
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    snoozeAlarm(activity)
                })
            {
                Text(text = "Snooze")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {

                    shutDownAlarm(activity)

                })
            {
                Text(text = "Go Home")
            }
        }
    }
}

fun shutDownAlarm(context: Context) {

    val serviceIntent = Intent(context,AlarmService::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.stopService(serviceIntent)

    val notificationManager =
        context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(1)

    val homeIntent = Intent(context, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.startActivity(homeIntent)
}

fun snoozeAlarm(context: Context){
    val serviceIntent = Intent(context,AlarmService::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.stopService(serviceIntent)

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(1)

    val calendar = Calendar.getInstance()
    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
    var minute = calendar.get(Calendar.MINUTE)

    minute = minute + 1

    setAlarm(context,hourOfDay,minute)
}