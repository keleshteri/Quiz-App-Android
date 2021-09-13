package com.keleshteri.quizandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class QuizQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        //get all Questions
        val questionsList = Constants.getQuestions()

        Log.i("Questions Size ","${questionsList.size}")
    }
}