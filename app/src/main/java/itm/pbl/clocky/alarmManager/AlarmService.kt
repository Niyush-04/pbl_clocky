package itm.pbl.clocky.alarmManager

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import itm.pbl.clocky.R
import itm.pbl.clocky.notification.NotificationService

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var alarmNotification: NotificationService

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound)
        mediaPlayer.isLooping = true

        vibrator = getSystemService(Vibrator::class.java)
        alarmNotification = NotificationService(this)
        Log.d(TAG,"onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"onStart")
        alarmNotification.showNotification("Alarm, It's time to wake up!")
        mediaPlayer.start()

        val activityIntent = Intent(this,
            AlarmActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        this.startActivity(activityIntent)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "AlarmService"
    }
}