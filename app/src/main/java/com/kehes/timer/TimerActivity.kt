package com.kehes.timer

import android.graphics.Color
import android.media.MediaPlayer
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
    private var timer: CountDownTimer? = null

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
        savedInstanceState?.let {
            timeToEnd = it.getLong(State.TIMETOEND.name)
            running = it.getBoolean(State.RUNNING.name)
            onPause = it.getBoolean(State.ONPAUSE.name)
            autoReset = it.getBoolean(State.AUTORESET.name)

            if (running && !onPause)
                runTimer()
            else
                timeView(timeToEnd)
        }
    }

    private fun runTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(timeToEnd, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if (running) {
                    timeView(millisUntilFinished)
                    timeToEnd = millisUntilFinished
                }
                else {
                    cancel()
                }
//              Log.e("Time", ">>> time: ${millisUntilFinished/1000}")
            }

            override fun onFinish() {
                ringTimer()
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
        if (timeToEnd > 0) {
            running = true
            onPause = false
            runTimer()
        }
    }

    private fun pauseClick() {
        running = false
        onPause = true
        timer?.cancel()
    }

    private fun resetClick() {
        running = false
        onPause = false
        timeToEnd = originTimeToEnd
        timer?.cancel()
        timeView(originTimeToEnd)
    }

    private fun autoResetClick() {
        autoReset = !autoReset
        if (autoReset)
            binding.autoResetBtn.setBackgroundColor(Color.GREEN)
        else
            binding.autoResetBtn.setBackgroundColor(Color.RED)
    }

    private fun ringTimer() {
        val sound = MediaPlayer.create(this, R.raw.bong)
        sound.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(State.TIMETOEND.name, timeToEnd)
        outState.putBoolean(State.RUNNING.name, running)
        outState.putBoolean(State.ONPAUSE.name, onPause)
        outState.putBoolean(State.AUTORESET.name, autoReset)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}

enum class State{
    RUNNING, TIMETOEND, ONPAUSE, AUTORESET
}