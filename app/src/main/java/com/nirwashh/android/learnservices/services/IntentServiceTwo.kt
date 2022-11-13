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

class IntentServiceTwo : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 1 until 5) {
            Thread.sleep(1000)
            log("Timer: $i - page$page")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d(TAG, "IntentServiceTwo: $message")
    }

    companion object {
        private const val TAG = "MyTag"
        private const val NAME = "INTENT_SERVICE"
        private const val PAGE = "page"
        fun newInstance(context: Context, page: Int) = Intent(context, IntentService::class.java).apply {
            putExtra(PAGE, page)
        }
    }
}