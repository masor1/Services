package com.masorone.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyIntentService : IntentService(NAME_SERVICE) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i")
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Title")
        .setContentText("Service worked")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun log(message: String) = Log.d("SERVICE_TAG", "${javaClass.simpleName}: $message")

    companion object {

        private const val NAME_SERVICE = "MyIntentService"

        private const val NOTIFICATION_ID = 1

        private const val CHANNEL_ID = "channel_foreground_id"
        private const val CHANNEL_NAME = "channel_foreground_name"

        fun newIntent(context: Context) = Intent(context, MyIntentService::class.java)
    }
}