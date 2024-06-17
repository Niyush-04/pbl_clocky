package itm.pbl.clocky.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import itm.pbl.clocky.alarmManager.AlarmReceiver
import itm.pbl.clocky.alarmManager.setAlarm
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    activity: AppCompatActivity,
    alarmViewModel: AlarmViewModel1,
    regBoolViewModel: ABoolViewModel
) {
    val currentTime = Calendar.getInstance()


    var regAlarmTime by remember {
        alarmViewModel::regAlarmTime
    }

    var regAlarmSet by remember {
        regBoolViewModel::regAlarmSet
    }

    val timePickerState = remember {
        TimePickerState(
            is24Hour = true,
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE)
        )

    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .navigationBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(8.dp))

            if (regAlarmSet) {
                Text(
                text = "Selected Time $regAlarmTime",
                )
            } else  {
                Text(
                text = "No AlarmSet"
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .weight(3f)
            ) {
                TimePicker(
                    state = timePickerState
                )
            }

            if(!regAlarmSet){
                Button(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute

                        regAlarmTime = "%02d:%02d".format(hour, minute)
                        regAlarmSet = !regAlarmSet
                        setAlarm(activity, hour, minute)

                    },
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()

                ) {
                    Text("Set Regular Alarm")
                }
            }

            // Cancel Alarm - if alarm is set, alarmSet Bool is true, so button functionality
            // is only to cancel.
            if (regAlarmSet) {
                Button(
                    onClick = {
                        cancelAlarm(activity)
                        regAlarmSet = !regAlarmSet
                    },
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()

                ) {
                    Text("Cancel Regular Alarm")
                }
            }

        }

    }


}



//Cancel alarm
fun cancelAlarm(context: Context) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("NotificationMessage", "Test")
    }

    // Create pending intent with same name and requestCode to cancel.
    val alarmPendingintent = PendingIntent
        .getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    alarmManager.cancel(alarmPendingintent)

}


// View Model to store the user selected regular alarm time.
class AlarmViewModel1 : ViewModel() {

    var regAlarmTime: String by mutableStateOf(LocalTime.NOON.toString())
}

class ABoolViewModel : ViewModel() {
    var regAlarmSet: Boolean by  mutableStateOf(false)
}

class DynamicViewModel : ViewModel() {
    var dynAlarmTime: String by mutableStateOf("08:00")
}

class DBoolViewModel : ViewModel() {
    var dynAlarmSet: Boolean by  mutableStateOf(false)
}



