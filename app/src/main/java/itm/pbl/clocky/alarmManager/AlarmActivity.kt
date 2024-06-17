package itm.pbl.clocky.alarmManager

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itm.pbl.clocky.MainActivity
import itm.pbl.clocky.presentation.alarm.AlarmAnimation
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

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
        , contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment =
            Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Wake up!",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.padding(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .scale(1.5f)
            ) {
                AlarmAnimation()
            }

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
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

    minute += 1

    setAlarm(context,hourOfDay,minute)
}