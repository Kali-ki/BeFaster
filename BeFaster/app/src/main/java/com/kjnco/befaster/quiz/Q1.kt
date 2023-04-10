package com.kjnco.befaster.quiz

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class Q1: AppCompatActivity() {

    // Declare a Quiz handler
    private var quizHandler = QuizHandler()

    // Declare the question and the answers text views
    private lateinit var question: TextView
    private lateinit var answer1: RadioButton
    private lateinit var answer2: RadioButton
    private lateinit var answer3: RadioButton

    // Declare the radio group
    private lateinit var radioGroup: RadioGroup

    // Declare the validation button
    private lateinit var validationButt: Button

    // Declare variables to store the time
    var startTime: Long = 0
    var answerTime: Long = 0

    // Declare if whether the answer is correct or not
    var isCorrect: Boolean = false

    // Declare launcher
    private lateinit var answerActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fast_quiz_question)

        // Create a QuizHandler
        val quizHandler = QuizHandler()

        // Get the TextView, RadioButton and Button
        question = findViewById(R.id.question)
        radioGroup = findViewById(R.id.answers)
        answer1 = findViewById(R.id.answer_1)
        answer2 = findViewById(R.id.answer_2)
        answer3 = findViewById(R.id.answer_3)
        validationButt = findViewById(R.id.validation)


        // Set the Button text
        validationButt.setText(R.string.valid_question)

        // Define the contract to pass the time to the next activity
        answerActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                answerTime = result.data?.getLongExtra("answerTime", 0) ?: 0
            }
        }

        // Iterate over the questions
        displayNextQuestion(0)
    }

    /**
     * Function to set the question and the answers
     */
    private fun pickAQuestion(i: Int) {
        question.setText(quizHandler.questionList.keys.elementAt(i))
        answer1.setText(quizHandler.questionList.values.elementAt(i)[0])
        answer2.setText(quizHandler.questionList.values.elementAt(i)[1])
        answer3.setText(quizHandler.questionList.values.elementAt(i)[2])
    }

    /**
     * Function to check the answer
     */
    private fun checkTheAnswer(index: Int, answer: Int) {

        // Getting the question id
        val questionId = quizHandler.questionList.keys.elementAt(index)
        // Getting the correct answer id
        val goodAnswerId = quizHandler.correctAnswerList[questionId]

        when (answer) {
            R.id.answer_1 -> {
                // Getting the current answer id
                val currentAnswerId = quizHandler.questionList.values.elementAt(index)[0]
                if (currentAnswerId == goodAnswerId) {
                    // Stop counting the time
                    val endTime = Date().time
                    // Calculate the time
                    val time = endTime - startTime
                    // Convert the time in seconds
                    val timeInSeconds = time / 1000
                    // Setting the answer properties
                    isCorrect = true
                    answerTime = timeInSeconds
                }else {
                    // Stop counting the time
                    val endTime = Date().time
                    // Calculate the time
                    val time = endTime - startTime
                    // Convert the time in seconds
                    val timeInSeconds = time / 1000
                    // Setting the answer properties
                    isCorrect = false
                    answerTime = timeInSeconds
                }
            }
            R.id.answer_2 -> {
                // Getting the current answer id
                val currentAnswerId = quizHandler.questionList.values.elementAt(index)[1]
                if (currentAnswerId == goodAnswerId) {
                    // Stop counting the time
                    val endTime = Date().time
                    // Calculate the time
                    val time = endTime - startTime
                    // Convert the time in seconds
                    val timeInSeconds = time / 1000
                    // Setting the answer properties
                    isCorrect = true
                    answerTime = timeInSeconds
                } else {
                    // Stop counting the time
                    val endTime = Date().time
                    // Calculate the time
                    val time = endTime - startTime
                    // Convert the time in seconds
                    val timeInSeconds = time / 1000
                    // Generate the Toast of wrong answer
                    // Setting the answer properties
                    isCorrect = false
                    answerTime = timeInSeconds
                }
            }
            R.id.answer_3 -> {
                // Getting the current answer id
                val currentAnswerId = quizHandler.questionList.values.elementAt(index)[2]
                if (currentAnswerId == goodAnswerId) {
                    // Stop counting the time
                    val endTime = Date().time
                    // Calculate the time
                    val time = endTime - startTime
                    // Convert the time in seconds
                    val timeInSeconds = time / 1000
                    // Setting the answer properties
                    isCorrect = true
                    answerTime = timeInSeconds
                }else {
                    // Stop counting the time
                    val endTime = Date().time
                    // Calculate the time
                    val time = endTime - startTime
                    // Convert the time in seconds
                    val timeInSeconds = time / 1000
                    // Generate the Toast of wrong answer
                    // Setting the answer properties
                    isCorrect = false
                    answerTime = timeInSeconds
                }
            }
        }
    }

    private fun displayNextQuestion(index: Int) {
        if (index > quizHandler.questionList.size) {
            return
        }
        pickAQuestion(index)
        startTime = Date().time

        validationButt.setOnClickListener {
            val selectedRadioId = radioGroup.checkedRadioButtonId
            if (selectedRadioId == -1) {
                Toast.makeText(applicationContext, "Il faut choisir une réponse !", Toast.LENGTH_SHORT).show()
            }else {
                checkTheAnswer(index, selectedRadioId)

                GlobalScope.launch {
                    startAnswerActivity()
                }
                displayNextQuestion(index + 1)
            }
        }
    }

    /**
     * Function to start the answer activity
     */
    private fun startAnswerActivity() {

        if (isCorrect) {
            val intent = Intent(this, RightAnswer::class.java)
            intent.putExtra("answerTime", answerTime)
            answerActivityLauncher.launch(intent)
        } else {
            val intent = Intent(this, WrongAnswer::class.java)
            intent.putExtra("answerTime", answerTime)
            answerActivityLauncher.launch(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        radioGroup.clearCheck()
    }

    /**
     * Empty function to avoid the error
     */
    fun onRadioButtonClicked(view: View) {
    }

}

