package com.kehes.timer

import android.content.Intent
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
                val intent = Intent(this, TimerActivity::class.java)
                intent.putExtra(ArgumentKey.SECONDS.name, binding.secondsInputView.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "You need input seconds", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpSendNumberButton() {
        binding.sendNumberBtn.setOnClickListener {
            if (isValid()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, binding.secondsInputView.text.toString())
                intent.type = "text/plain"
                val choseIntent = Intent.createChooser(intent, "Sharing number")
                startActivity(choseIntent)
            } else {
                Toast.makeText(this, "You need input seconds", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid() = !binding.secondsInputView.text.isNullOrBlank()
}

enum class ArgumentKey() {
    SECONDS
}