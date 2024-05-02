package itm.pbl.clocky

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import itm.pbl.clocky.navigation.Constants.NOTIFICATION_CHANNEL_ID
import itm.pbl.clocky.navigation.Constants.NOTIFICATION_CHANNEL_NAME

@HiltAndroidApp
class ClockyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(notificationChannel)
    }
}