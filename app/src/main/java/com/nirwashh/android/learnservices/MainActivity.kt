package com.nirwashh.android.learnservices

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.nirwashh.android.learnservices.databinding.ActivityMainBinding
import com.nirwashh.android.learnservices.services.*
import com.nirwashh.android.learnservices.services.MyJobService.Companion.JOB_ID
import com.nirwashh.android.learnservices.services.MyWorker.Companion.WORK_NAME
import com.nirwashh.android.learnservices.services.MyWorker.Companion.makeRequest

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnService.setOnClickListener {
            startService(SimpleService.newIntent(this))
        }

        binding.btnForegroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, ForegroundService.newInstance(this))
        }

        binding.btnIntentService.setOnClickListener {
            ContextCompat.startForegroundService(this, IntentService.newInstance(this))
        }

        binding.btnJobScheduler.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)
            val jobInfo = JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            //jobScheduler.schedule(jobInfo)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = MyJobService.newIntent(page++)
                jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
            } else {
                startService(IntentServiceTwo.newInstance(this, page++))
            }
        }

        binding.btnJobIntentService.setOnClickListener {
            MyJobIntentService.enqueue(this, page++)
        }

        binding.btnWorkManager.setOnClickListener {
            val workManager = WorkManager.getInstance(applicationContext)
            workManager.enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.APPEND,
                makeRequest(page++)
            )
        }
    }
}