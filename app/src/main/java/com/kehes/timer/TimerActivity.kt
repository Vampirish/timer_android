package com.kehes.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kehes.timer.databinding.ActivityTimerBinding
import java.util.Locale

class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private var originTimeToEnd: Long = 0
    private val countDownInterval: Long = 1000
    private var running: Boolean = false
    private var onPause: Boolean = true
    private var autoReset: Boolean = false
    private var timeToEnd : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val timeToEndStr = it.getString(ArgumentKey.SECONDS.name).toString()
            originTimeToEnd = timeToEndStr.toLong() * 1000
            timeToEnd = originTimeToEnd
        }
        timeView(originTimeToEnd)

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
            autoResetBtn.setOnClickListener {
                autoResetClick()
            }
        }
    }

    private fun runTimer() {
        object : CountDownTimer(timeToEnd, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if (running)
                    timeView(millisUntilFinished)
                else {
                    if (onPause)
                        timeToEnd = millisUntilFinished
                    cancel()
                }
//              Log.e("Time", ">>> time: ${millisUntilFinished/1000}")
            }

            override fun onFinish() {
                binding.timeView.text = "00:00"
                timeToEnd = 0
                if (autoReset) {
                    timeToEnd = originTimeToEnd + 1000
                    runTimer()
                }
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
        onPause = false
    }

    private fun pauseClick() {
        running = false
        onPause = true
    }

    private fun resetClick() {
        running = false
        onPause = false
        timeToEnd = originTimeToEnd
        timeView(originTimeToEnd)
    }

    private fun autoResetClick() {
        autoReset = !autoReset
    }
}