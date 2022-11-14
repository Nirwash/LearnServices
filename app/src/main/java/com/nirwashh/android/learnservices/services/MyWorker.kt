package com.nirwashh.android.learnservices.services

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 20) {
            Thread.sleep(500)
            log("Timer: $i page: $page")

        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d(TAG, "MyWorker: $message")
    }

    companion object {
        private const val TAG = "MyTag"
        private const val PAGE = "page"
        const val WORK_NAME = "MyWorker"

        fun makeRequest(page: Int) = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(workDataOf(PAGE to page))
            .setConstraints(makeConstraints())
            .build()

        private fun makeConstraints() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
    }
}