package com.kehes.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kehes.timer.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var binding: ActivityTimerBinding
    private lateinit var countDownTimer: CountDownTimer
    private var timeToEnd: Int = 0
    private var countDownInterval: Long = 1000
    private var running: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val timeToEndStr = it.getString(ArgumentKey.SECONDS.name).toString()
            timeToEnd = timeToEndStr.toInt()
        }
        textView = findViewById(R.id.time_view)

/*
        object : CountDownTimer(timeToEnd, countDownInterval) {
            override fun onTick(p0: Long) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                mTextField.setText("done!")
            }

        }.start()
*/


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

        // runTimer()
    }

    private fun runTimer() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                TODO("Not yet implemented")
            }
        })
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