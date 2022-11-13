package com.nirwashh.android.learnservices.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.nirwashh.android.learnservices.R
import kotlinx.coroutines.*

class ForegroundService : Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 1 until 20) {
                delay(1000)
                log("Timer: $i")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        coroutineScope.cancel()
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
        Log.d(TAG, message)
    }


    companion object {
        private const val TAG = "FOREGROUND_SERVICE"
        private const val CHANNEL_ID = "channel ID"
        private const val CHANNEL_NAME = "channel name"
        private const val NOTIFICATION_ID = 1
        fun newInstance(context: Context): Intent {
            return Intent(context, ForegroundService::class.java)
        }
    }
}