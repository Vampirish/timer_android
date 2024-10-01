package com.kehes.timer

import android.os.Bundle
import android.os.CountDownTimer
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

        object : CountDownTimer(timeToEnd, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val min = (millisUntilFinished / 60000) % 60
                val sec = (millisUntilFinished / 1000) % 60
                val time = String.format(Locale.getDefault(),"%02d:%02d", min, sec)
                binding.timeView.text = time
            }

            override fun onFinish() {
                binding.timeView.text = "00:00"
            }
        }.start()


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

    private fun startClick() {
        running = true
    }

    private fun pauseClick() {
        running = false
    }

    private fun resetClick() {
        running = false
    }

}