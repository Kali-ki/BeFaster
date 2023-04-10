package com.kjnco.befaster.quiz

import com.kjnco.befaster.R

class QuizHandler {

    // Storing question id as key and answers id as values
    var questionList = mutableMapOf<Int, List<Int>>()

    // Storing question id as key and correct answer id as value
    val correctAnswerList = hashMapOf<Int, Int>()

    // Declare the number of question to iterate on
    val numberOfQuestions: Int = 6

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



}