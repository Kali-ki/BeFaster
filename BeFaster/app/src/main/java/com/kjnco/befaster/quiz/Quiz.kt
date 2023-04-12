package com.kjnco.befaster.quiz

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class Quiz: AppCompatActivity(){

    // Storing question id as key and answers id as values
    public var questionList = mutableMapOf<Int, List<Int>>()

    // Storing question id as key and correct answer id as value
    public val correctAnswerList = hashMapOf<Int, Int>()

    // Declare the number of question to iterate on
    val numberOfQuestions: Int = 6

    // Declare the ViewPager2 and the fragment

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
        // Setting up a Bundle
        val bundle = Bundle()
        bundle.putInt("question", questionList.keys.elementAt(0))
        bundle.putInt("correctAnswer", correctAnswerList[questionList.keys.elementAt(0)]?:0)
        bundle.putInt("answer1", questionList[questionList.keys.elementAt(0)]?.get(0)?:0)
        bundle.putInt("answer2", questionList[questionList.keys.elementAt(0)]?.get(1)?:0)
        bundle.putInt("answer3", questionList[questionList.keys.elementAt(0)]?.get(2)?:0)
        val question_fragment = QuestionFragment()
        // Passing the question and the answers to the fragment
        question_fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.question_container, question_fragment)
            .commit()

        // Setting up
        val congrats_fragment = CongratsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.congrats_container, congrats_fragment)
            .commit()


    }


}