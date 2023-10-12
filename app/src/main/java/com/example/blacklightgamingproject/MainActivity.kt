package com.example.blacklightgamingproject

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.blacklightgamingproject.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var timer: Timer? = null
    private var isClicked: Boolean = true
    private lateinit var binding: ActivityMainBinding
    private var greyPosition = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenerInIt()
        startTimer()
    }

    private fun runCondition(randomNumber: Int) {
        greyPosition = randomNumber
        when (randomNumber) {
            1 -> {
                binding.square1.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.light_gray
                    )
                )
            }

            2 -> {
                binding.square2.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.light_gray
                    )
                )
            }

            3 -> {
                binding.square3.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.light_gray
                    )
                )
            }

            4 -> {
                binding.square4.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.light_gray
                    )
                )
            }

        }
    }

    private fun listenerInIt() {
        binding.square1.setOnClickListener(this)
        binding.square2.setOnClickListener(this)
        binding.square3.setOnClickListener(this)
        binding.square4.setOnClickListener(this)
        binding.gameOver.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.square1 -> {
                if (greyPosition == 1) {
                    increaseScore()
                    isClicked = true
                    binding.square1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.red))
                } else gameOver()
            }

            binding.square2 -> {
                if (greyPosition == 2) {
                    increaseScore()
                    isClicked = true
                    binding.square2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.blue))
                } else gameOver()
            }

            binding.square3 -> {
                if (greyPosition == 3) {
                    increaseScore()
                    isClicked = true
                    binding.square3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.yellow))
                } else gameOver()
            }

            binding.square4 -> {
                if (greyPosition == 4) {
                    increaseScore()
                    isClicked = true
                    binding.square4.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.green))
                } else gameOver()
            }

            binding.gameOver -> {
                binding.gameOver.visibility = View.GONE
                restartTimer()
            }
        }
    }

    private fun gameOver() {
        runOnUiThread {
            binding.gameOver.visibility = View.VISIBLE
            binding.gameOver.text = "Game Over \n Your Score is $score \n \n Click to Restart"
        }
        stopTimer()
    }

    private fun restartTimer() {
        isClicked = true
        stopTimer()
        resetScreen()
        startTimer()
    }

    private fun resetScreen() {
        score = 0
        greyPosition = 0
        isClicked = true
        binding.square1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.red))
        binding.square2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.blue))
        binding.square3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.yellow))
        binding.square4.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@MainActivity, R.color.green))
        binding.score.text = "Score : $score"
    }

    private fun startTimer() {
        lifecycleScope.launch {
            Toast.makeText(this@MainActivity, "Game is started", Toast.LENGTH_SHORT).show()
            delay(3000)
            activate()
        }
    }

    private fun activate() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (isClicked) isClicked = false
                else gameOver()
                val random = Random.Default
                val randomNumber = random.nextInt(1, 5)
                runCondition(randomNumber)
            }
        }, 0, 1000)
    }


    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    private fun increaseScore() {
        score++
        binding.score.text = "Score : $score"
    }

}