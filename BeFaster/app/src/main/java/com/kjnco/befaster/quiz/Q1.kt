package com.kjnco.befaster.quiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import java.util.*

class Q1: AppCompatActivity() {

    // Declare variables to store the time
    var startTime: Long = 0
    var answerTime: Long = 0

    // Declare if whether the answer is correct or not
    var isCorrect: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fast_quiz_question)

        // Create a QuizHandler
        val quizHandler = QuizHandler()

        // Start counting the time
        startTime = Date().time

        // Get the TextView, RadioButton and Button
        val question: TextView by lazy { findViewById(R.id.question)}
        val radioGroup: RadioGroup by lazy { findViewById(R.id.answers)}
        val answer1: RadioButton by lazy { findViewById(R.id.answer_1)}
        val answer2: RadioButton by lazy { findViewById(R.id.answer_2)}
        val answer3: RadioButton by lazy { findViewById(R.id.answer_3)}
        val validationButt: Button by lazy { findViewById(R.id.validation)}


        // Set the TextView, RadioButton and Button with the first question
        question.setText(quizHandler.questionList.keys.first())
        answer1.setText(quizHandler.questionList.values.first()[0])
        answer2.setText(quizHandler.questionList.values.first()[1])
        answer3.setText(quizHandler.questionList.values.first()[2])
        validationButt.setText(R.string.valid_question)

        // Getting the radio selected id
        if (radioGroup.childCount > 0) {
            validationButt.setOnClickListener {
                val selectedRadioId = radioGroup.checkedRadioButtonId
                var goodAnswer = false
                if (selectedRadioId == -1) {
                    Toast.makeText(applicationContext, "Il faut choisir une réponse !", Toast.LENGTH_SHORT).show()
                } else {
                    checkTheAnswer(quizHandler, selectedRadioId)
                }
                if(goodAnswer) {
                    val rightActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            val answerTime = result.data?.getLongExtra("answerTime", 0) ?: 0

                        }
                    }
                    val intent = Intent(this, RightAnswer::class.java)
                    intent.putExtra("answerTime", answerTime)
                    rightActivity.launch(intent)
                }else {
                    val wrongActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            val answerTime = result.data?.getLongExtra("answerTime", 0) ?: 0

                        }
                    }
                    val intent = Intent(this, WrongAnswer::class.java)
                    intent.putExtra("answerTime", answerTime)
                    wrongActivity.launch(intent)
                }
            }
        }else {
            Log.e("MainActivity", "No radio buttons found in radio group")
        }

    }

    private fun checkTheAnswer(qh: QuizHandler, answer: Int) {

        // Getting the question id
        val questionId = qh.questionList.keys.first()
        // Getting the correct answer id
        val goodAnswerId = qh.correctAnswerList[questionId]

        when (answer) {
            R.id.answer_1 -> {
                // Getting the current answer id
                val currentAnswerId = qh.questionList.values.first()[0]
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
                val currentAnswerId = qh.questionList.values.first()[1]
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
                val currentAnswerId = qh.questionList.values.first()[2]
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

    fun onRadioButtonClicked(view: View) {
    }
}

