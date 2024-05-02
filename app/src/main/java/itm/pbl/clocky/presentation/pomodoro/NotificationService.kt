package itm.pbl.clocky.presentation.pomodoro

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import itm.pbl.clocky.MainActivity
import itm.pbl.clocky.R
import itm.pbl.clocky.navigation.Constants.NOTIFICATION_CHANNEL_ID
import itm.pbl.clocky.navigation.Constants.NOTIFICATION_ID
import itm.pbl.clocky.navigation.Constants.REQUEST_CODE


class NotificationService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val myIntent = Intent(context, MainActivity::class.java)
    private val pendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        myIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    fun showNotification(text: String) {

        val notification =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Pomodoro Timer")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}