package com.nirwashh.android.learnservices.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(500)
            log("Timer: $i page: $page")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d(TAG, "JobIntentService: $message")
    }

    companion object {
        private const val TAG = "MyTag"
        private const val PAGE = "page"
        private const val JOB_ID = 11
        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID, newInstance(context, page)
            )
        }

        private fun newInstance(context: Context, page: Int) =
            Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
    }
}