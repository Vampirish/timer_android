package com.kehes.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kehes.timer.databinding.ActivityTimerBinding
import java.util.Locale

class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private var timeToEnd: Long = 0
    private var countDownInterval: Long = 1000
    private var running: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val timeToEndStr = it.getString(ArgumentKey.SECONDS.name).toString()
            timeToEnd = timeToEndStr.toLong() * 1000
        }
        timeView(timeToEnd)

        with(binding) {
            startBtn.setOnClickListener {
                startClick()
            }
            pauseBtn.setOnClickListener {
                pauseClick()
            }
            resetBtn.setOnClickListener {
                resetClick()
            }
        }
    }

    private fun runTimer() {
        object : CountDownTimer(timeToEnd, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if (running)
                    timeView(millisUntilFinished)
                else
                    timeView(millisUntilFinished)
                    onFinish()
//                Log.e("Time", ">>> time: ${millisUntilFinished/1000}")
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun timeView(millis: Long) {
        val min = (millis / 60000) % 60
        val sec = (millis / 1000) % 60
        val time = String.format(Locale.getDefault(),"%02d:%02d", min, sec)
        binding.timeView.text = time
    }

    private fun startClick() {
        runTimer()
        running = true
    }

    private fun pauseClick() {
        running = false
    }

    private fun resetClick() {
        running = false
    }

}