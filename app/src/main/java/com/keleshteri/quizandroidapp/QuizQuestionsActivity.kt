package com.keleshteri.quizandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.keleshteri.quizandroidapp.databinding.ActivityMainBinding
import com.keleshteri.quizandroidapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_quiz_questions)
        setContentView(binding.root)

        //get all Questions
        val questionsList = Constants.getQuestions()

        Log.i("Questions Size ","${questionsList.size}")

        /**  **/
        val currentPosition=1
        val question:Question? = questionsList[currentPosition-1]


        binding.progressBar.progress= currentPosition
        binding.tvProgress.text = "$currentPosition" + "/"+binding.progressBar.max

        binding.tvQuestion.text = question!!.question
        binding.ivImage.setImageResource(question.image)

        binding.tvOptionOne.text=question.optionOne
        binding.tvOptionTwo.text=question.optionTwo
        binding.tvOptionThree.text=question.optionThree
        binding.tvOptionFour.text =question.optionFour

    }
}