package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    var binding: ActivityResultBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)

        val username = intent.getStringExtra(Constants.USER_NAME)
        binding!!.tvUserName.text = username

        val totalQuestions = intent.getStringExtra(Constants.TOTAL_QUESTIONS)
        val correctAnswers = intent.getStringExtra(Constants.CORRECT_ANSWER)

        binding!!.tvScore.text = "Your score is $correctAnswers out of $totalQuestions"
        binding!!.btsFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}