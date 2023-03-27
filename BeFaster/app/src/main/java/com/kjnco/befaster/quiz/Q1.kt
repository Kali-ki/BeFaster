package com.kjnco.befaster.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import java.util.*

class Q1: AppCompatActivity() {

    // Storing question id as key and answers id as values
    val questionList = hashMapOf<Int, List<Int>>()

    // Storing question id as key and correct answer id as value
    val correctAnswerList = hashMapOf<Int, Int>()

    // Declare the variable to store the time
    var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fast_quiz_question)

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

        // Start counting the time
        startTime = Date().time

        // Get the TextView, RadioButton and Button
        val question: TextView by lazy { findViewById(R.id.question)}
        val answer1: RadioButton by lazy { findViewById(R.id.answer_1)}
        val answer2: RadioButton by lazy { findViewById(R.id.answer_2)}
        val answer3: RadioButton by lazy { findViewById(R.id.answer_3)}
        val validationButt: Button by lazy { findViewById(R.id.validation)}

        // Shuffle the questionList
        val shuffledQuestionList = questionList.toList().shuffled().toMap()

        // Set the TextView, RadioButton and Button with the first question
        question.setText(shuffledQuestionList.keys.first())
        answer1.setText(shuffledQuestionList.values.first()[0])
        answer2.setText(shuffledQuestionList.values.first()[1])
        answer3.setText(shuffledQuestionList.values.first()[2])
        validationButt.setText(R.string.valid_question)

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.answer_1 ->
                    if (checked) {
                        // Generate Toast with bad remark
                        Toast.makeText(applicationContext,"Loser! ",Toast.LENGTH_SHORT).show()
                    }
                R.id.answer_2 ->
                    if (checked) {
                        Toast.makeText(applicationContext,"Loser! ",Toast.LENGTH_SHORT).show()
                    }
                R.id.answer_3 ->
                    if (checked) {
                        // Stop counting the time
                        val endTime = Date().time
                        // Calculate the time
                        val time = endTime - startTime
                        // Convert the time in seconds
                        val timeInSeconds = time / 1000
                        // Generate Toast with the congrats and perf
                        Toast.makeText(applicationContext,"Congrats! You did it in $timeInSeconds seconds",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}