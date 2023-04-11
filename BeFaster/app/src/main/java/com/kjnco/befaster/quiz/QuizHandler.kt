package com.kjnco.befaster.quiz

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class QuizHandler: AppCompatActivity(){

    // Storing question id as key and answers id as values
    var questionList = mutableMapOf<Int, List<Int>>()

    // Storing question id as key and correct answer id as value
    val correctAnswerList = hashMapOf<Int, Int>()

    // Declare the number of question to iterate on
    val numberOfQuestions: Int = 6

    // Declare the question and the answers text views
    private lateinit var question: TextView
    private lateinit var answer1: RadioButton
    private lateinit var answer2: RadioButton
    private lateinit var answer3: RadioButton

    // Declare the radio group
    private lateinit var radioGroup: RadioGroup

    // Declare the validation button
    private lateinit var validationButt: Button


    init {
        // Fill the questionList
        questionList.put(R.string.question_1, listOf(R.string.answer_1_1, R.string.answer_1_2, R.string.answer_1_3))
        questionList.put(R.string.question_2, listOf(R.string.answer_2_1, R.string.answer_2_2, R.string.answer_2_3))
        questionList.put(R.string.question_3, listOf(R.string.answer_3_1, R.string.answer_3_2, R.string.answer_3_3))
        questionList.put(R.string.question_4, listOf(R.string.answer_4_1, R.string.answer_4_2, R.string.answer_4_3))
        questionList.put(R.string.question_5, listOf(R.string.answer_5_1, R.string.answer_5_2, R.string.answer_5_3))
        questionList.put(R.string.question_6, listOf(R.string.answer_6_1, R.string.answer_6_2, R.string.answer_6_3))

        // Fill the correctAnswerList
        correctAnswerList.put(R.string.question_1, R.string.answer_1_3)
        correctAnswerList.put(R.string.question_2, R.string.answer_2_3)
        correctAnswerList.put(R.string.question_3, R.string.answer_3_3)
        correctAnswerList.put(R.string.question_4, R.string.answer_4_3)
        correctAnswerList.put(R.string.question_5, R.string.answer_5_3)
        correctAnswerList.put(R.string.question_6, R.string.answer_6_3)
    }

    init {
        // Shuffle the questionList
        questionList = questionList.toList().shuffled().toMap().toMutableMap()

        // Iterate over the shuffledQuestionList
        for ((key, value) in questionList) {
            // Shuffle the answers
            val shuffledAnswerList = value.shuffled()
            // Replace the answers in the shuffledQuestionList
            questionList.put(key, shuffledAnswerList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fast_quiz_question)

        // Get the TextView, RadioButton and Button
        question = findViewById(R.id.question)
        radioGroup = findViewById(R.id.answers)
        answer1 = findViewById(R.id.answer_1)
        answer2 = findViewById(R.id.answer_2)
        answer3 = findViewById(R.id.answer_3)
        validationButt = findViewById(R.id.validation)


        // Set the Button text
        validationButt.setText(R.string.valid_question)

    }


}