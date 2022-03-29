package com.example.app

import android.content.Intent
import  android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast.*
import androidx.core.content.ContextCompat
import com.example.app.databinding.ActivityMainBinding
import com.example.app.databinding.ActivityQuestionsBinding

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentposition:Int = 1
    private var mQuestionList:ArrayList<Question>? = null
    private var mSelectedoptionPosition:Int = 0
    private var mCorrectAnswer: Int = 0
    private var mUserName: String? =null


    var binding: ActivityQuestionsBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = Constants.getQuestions()

        setquestion()

        binding!!.tvOptionOne.setOnClickListener(this)
        binding!!.tvOptionTwo.setOnClickListener(this)
        binding!!.tvOptionThree.setOnClickListener(this)
        binding!!.tvOptionFour.setOnClickListener(this)
        binding!!.btnSubmit.setOnClickListener(this)
    }

    private fun setquestion(){

        val question = mQuestionList!![mCurrentposition - 1]

        defaultOptionsView()

        if(mCurrentposition == mQuestionList!!.size){
            binding!!.btnSubmit.text = "FINISH"
        }else{
            binding!!.btnSubmit.text = "SUBMIT"
        }

        val progressbar = binding!!.progressBar

        progressbar.progress = mCurrentposition
        binding!!.tvProgress.text = "$mCurrentposition" + "/" + progressbar.max

        binding!!.tvQuestion.text = question!!.question
        binding!!.ivImage.setImageResource(question.image)
        binding!!.tvOptionOne.text = question.optionOne
        binding!!.tvOptionTwo.text = question.optionTwo
        binding!!.tvOptionThree.text = question.optionThree
        binding!!.tvOptionFour.text = question.optionFour


    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, binding!!.tvOptionOne)
        options.add(1, binding!!.tvOptionTwo)
        options.add(2, binding!!.tvOptionThree)
        options.add(3, binding!!.tvOptionFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.border_default_option
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvOptionOne ->{
                selectedOptionView(binding!!.tvOptionOne,1)
            }
            R.id.tvOptionTwo ->{
                selectedOptionView(binding!!.tvOptionTwo,2)
            }
            R.id.tvOptionThree ->{
                selectedOptionView(binding!!.tvOptionThree,3)
            }
            R.id.tvOptionFour ->{
                selectedOptionView(binding!!.tvOptionFour,4)
            }
            R.id.btnSubmit ->{
                if (mSelectedoptionPosition == 0){
                    mCurrentposition++

                    when{
                         mCurrentposition <= mQuestionList!!.size ->{
                             setquestion()
                         }else ->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWER, mCorrectAnswer)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList)
                            startActivity(intent)
                         }
                    }
                }else{
                    val  question = mQuestionList?.get(mCurrentposition - 1)
                    if(question!!.correctAnswer != mSelectedoptionPosition){
                        answerView(mSelectedoptionPosition, R.drawable.wrong_option_border)
                    }else{
                        mCorrectAnswer++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border)

                    if (mCurrentposition == mQuestionList!!.size){
                        binding!!.btnSubmit.text = "FINISH"
                    }else{
                        binding!!.btnSubmit.text = "GO TO THE NEXT QUESTION"
                    }
                    mSelectedoptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when(answer){
            1 ->{
                binding!!.tvOptionOne.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 ->{
                binding!!.tvOptionTwo.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 ->{
                binding!!.tvOptionThree.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 ->{
                binding!!.tvOptionFour.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView,
                                   selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedoptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border
        )
    }
}
