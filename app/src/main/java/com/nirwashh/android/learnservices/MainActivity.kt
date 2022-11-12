package com.nirwashh.android.learnservices

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nirwashh.android.learnservices.databinding.ActivityMainBinding
import com.nirwashh.android.learnservices.services.SimpleService

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnService.setOnClickListener {
            startService(SimpleService.newIntent(this))
        }
    }
}