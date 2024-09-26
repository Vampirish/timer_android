package com.kehes.timer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kehes.timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSetTimerButton()
        setUpSendNumberButton()
    }

    private fun setUpSetTimerButton() {
        binding.setTimerBtn.setOnClickListener {
            if (isValid()) {
                TODO()
            } else {
                Toast.makeText(this, "You need input seconds", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpSendNumberButton() {
        TODO()
    }

    private fun isValid() =!binding.secondsInputView.text.isNullOrBlank()
}