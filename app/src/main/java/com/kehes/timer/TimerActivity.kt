package com.kehes.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kehes.timer.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}