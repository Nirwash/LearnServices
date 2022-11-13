package com.nirwashh.android.learnservices.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.nirwashh.android.learnservices.R

class IntentService : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        for (i in 1 until 5) {
            Thread.sleep(1000)
            log("Timer: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

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

    private fun createNotification() = NotificationCompat.Builder(
        this, CHANNEL_ID
    )
        .setContentTitle("Foreground service")
        .setContentText("")
        .setSmallIcon(R.drawable.ic_foreground_service)
        .build()

    private fun log(message: String) {
        Log.d(TAG, "IntentService: $message")
    }

    companion object {
        private const val TAG = "MyTag"
        private const val NAME = "INTENT_SERVICE"
        private const val CHANNEL_ID = "channel ID"
        private const val CHANNEL_NAME = "channel name"
        private const val NOTIFICATION_ID = 1
        fun newInstance(context: Context) = Intent(context, IntentService::class.java)
    }
}