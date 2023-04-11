package com.kjnco.befaster.quiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import java.util.*

class Q1 {

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
    private var startTime: Long = 0
    var answerTime: Long = 0

    // Declare if whether the answer is correct or not
    var isCorrect: Boolean = false

    // Declare launcher
    private lateinit var answerActivityLauncher: ActivityResultLauncher<Intent>

    fun onCreate(savedInstanceState: Bundle?) {
        /*super.onCreate(savedInstanceState)
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
        }*/

        // Passing the question to the QuestionFragment

    }

    /**
     * Function to set the question and the answers
     */
    private fun assignTheQuestionToTheFragment(i: Int): Bundle? {
        /*
        val bundle = Bundle().apply{
            putString("question", resources.getString(quizHandler.questionList.keys.elementAt(i)))
            putString("answer1", resources.getString(quizHandler.questionList.values.elementAt(i)[0]))
            putString("answer2", resources.getString(quizHandler.questionList.values.elementAt(i)[1]))
            putString("answer3", resources.getString(quizHandler.questionList.values.elementAt(i)[2]))
        }
        return bundle*/
        return null
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

    private fun throwTheNextQuestion(view: View, index: Int) {
        if (index > quizHandler.questionList.size) {
            return
        }
        val bundle = assignTheQuestionToTheFragment(index)
        val questionFragment = QuestionFragment()
        questionFragment.arguments = bundle

    }

    /**
     * Function to start the answer fragment
     * It selects the right fragment depending on the answer
     */
    private fun startAnswerFragment(view: View) {
        /*
        val question_container = findViewById<FrameLayout>(R.id.container)
        question_container.visibility = View.GONE

        if (isCorrect) {
            val rightAnswer = RightAnswer.newInstance(answerTime)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, rightAnswer)
                .commit()

            view?.postDelayed({
                supportFragmentManager.beginTransaction()
                    .replace()
                    .commit()
            }, 2000)
        } else {
            val wrongAnswer = WrongAnswer.newInstance(answerTime)

            supportFragmentManager.beginTransaction()
                .add(R.id.container, wrongAnswer)
                .commit()

            showQuestionContainer(false)

            view?.postDelayed({
                supportFragmentManager.beginTransaction()
                    .remove(wrongAnswer)
                    .commit()
                showQuestionContainer(true)
            }, 2000)
        }*/
    }

    /**
     * Empty function to avoid the error
     */
    fun onRadioButtonClicked(view: View) {
    }

}

