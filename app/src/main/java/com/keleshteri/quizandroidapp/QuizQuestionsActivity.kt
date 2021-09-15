package com.keleshteri.quizandroidapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.keleshteri.quizandroidapp.databinding.ActivityMainBinding
import com.keleshteri.quizandroidapp.databinding.ActivityQuizQuestionsBinding
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), OnClickListener {

    //binding
    private lateinit var binding: ActivityQuizQuestionsBinding

    private var mCurrentPosition:Int = 1
    private var mQuestionsList: ArrayList<Question>? =null
    private var mSelectedOptionPosition:Int =0
    private var mCorrectAnswers: Int=0
    private var mUserName: String? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_quiz_questions)
        setContentView(binding.root)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN

        //
        mUserName = intent.getStringExtra(Constants.USER_NAME)

        //get all Questions
        mQuestionsList = Constants.getQuestions()

        setQuestion()

        //onclick
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)



    }

    /**
     * set Question
     */
    private fun setQuestion(){
        /**  **/
//        mCurrentPosition=1
        val question = mQuestionsList!![mCurrentPosition-1]

        //set default style for options
        defaultOptionsView()

        //set btnSubmit text
        if(mCurrentPosition== mQuestionsList!!.size){
            binding.btnSubmit.text="FINISH"
        }else{
            binding.btnSubmit.text="SUBMIT"
        }



        binding.progressBar.progress= mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition" + "/"+binding.progressBar.max

        binding.tvQuestion.text = question!!.question
        binding.ivImage.setImageResource(question.image)

        binding.tvOptionOne.text=question.optionOne
        binding.tvOptionTwo.text=question.optionTwo
        binding.tvOptionThree.text=question.optionThree
        binding.tvOptionFour.text =question.optionFour
    }

    /**
     * set default style for options
     */
    private fun defaultOptionsView(){
        // create array list TextView
        val options = ArrayList<TextView>()

        //added from activity_quiz_questions layout
        options.add(0,binding.tvOptionOne)
        options.add(1,binding.tvOptionTwo)
        options.add(2,binding.tvOptionThree)
        options.add(3,binding.tvOptionFour)

        //for Loop
        for (option in options){
            //set all style
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface =Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )

        }


    }

    /**
     * Selected Options
     */
    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
        //rest all style all options
        defaultOptionsView()

        mSelectedOptionPosition= selectedOptionNum

        //set style for selected option
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    /**
     * check answer and set drawableView
     */
    private fun answerView(answer:Int, drawableView:Int){

        when(answer){
            //if answer options 1
            1->{
                binding.tvOptionOne.background= ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            //if answer options 2
            2->{
                binding.tvOptionTwo.background= ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            //if answer options 3
            3->{
                binding.tvOptionThree.background= ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            //if answer options 4
            4->{
                binding.tvOptionFour.background= ContextCompat.getDrawable(
                    this,drawableView
                )
            }
        }
    }

    /**
     * Handle all OnClick in view
     */
    override fun onClick(v: View?) {
        when(v?.id){
            //if click option 1
            R.id.tv_option_one ->{
                selectedOptionView(binding.tvOptionOne,1)
            }
            //if click option 2
            R.id.tv_option_two ->{
                selectedOptionView(binding.tvOptionTwo,2)
            }
            //if click option 3
            R.id.tv_option_three ->{
                selectedOptionView(binding.tvOptionThree,3)
            }
            //if click option 4
            R.id.tv_option_four ->{
                selectedOptionView(binding.tvOptionFour,4)
            }
            //if click on submit
            R.id.btn_submit ->{
                //check if selected option
                if(mSelectedOptionPosition ==0){
                    mCurrentPosition++

                    //check
                    when{
                        mCurrentPosition <= mQuestionsList!!.size->{
                            setQuestion()
                        }else ->{
                          val intent = Intent(this,ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME,mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList!!.size)
                        startActivity(intent)
                        finish()
                        }
                    }
                    //check Else selected option !=0
                }else{
                    val question=mQuestionsList?.get(mCurrentPosition-1)
                    //check correctAnswer with SelectedOption
                    if (question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    }else{
                        //correct
                        mCorrectAnswers++
                    }
                    //set style correctAnswer
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)

                    //check if is last Question
                    if (mCurrentPosition==mQuestionsList!!.size){
                        binding.btnSubmit.text="FINISH"
                    }else{
                        binding.btnSubmit.text="GO TO NEXT QUESTION"
                    }
                    //set
                    mSelectedOptionPosition=0
                }
            }
        }
    }
}