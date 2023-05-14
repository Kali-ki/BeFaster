package com.kjnco.befaster.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kjnco.befaster.R
import java.util.Date

/**
 * A question fragment corresponding to one question
 *     and three answers.
 */
class QuestionFragment : Fragment(){

    // Question IDs
    private var question: Int = 0
    private var correctAnswer: Int = 0
    private var answer1: Int = 0
    private var answer2: Int = 0
    private var answer3: Int = 0

    private var correctAnswerIndex: Int = 0

    companion object {
        private const val ARG_QUESTION = "question"
        private const val ARG_ANSWER_1 = "answer1"
        private const val ARG_ANSWER_2 = "answer2"
        private const val ARG_ANSWER_3 = "answer3"
        private const val ARG_ANSWER = "correctAnswer"

        fun newInstance(question: Int, answer1: Int, answer2: Int, answer3: Int, correctAnswer: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_QUESTION, question)
            args.putInt(ARG_ANSWER_1, answer1)
            args.putInt(ARG_ANSWER_2, answer2)
            args.putInt(ARG_ANSWER_3, answer3)
            args.putInt(ARG_ANSWER, correctAnswer)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Getting the arguments
        question = arguments?.getInt("question")!!
        correctAnswer = arguments?.getInt("correctAnswer")!!
        answer1 = arguments?.getInt("answer1")!!
        answer2 = arguments?.getInt("answer2")!!
        answer3 = arguments?.getInt("answer3")!!

        // Determining the index of the correct answer
        correctAnswerIndex = when (correctAnswer) {
            answer1 -> 1
            answer2 -> 2
            answer3 -> 3
            else -> 0
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        // Getting the elements of the layout
        val questionTV: TextView = view.findViewById(R.id.question) as TextView
        val radioGroup: RadioGroup = view.findViewById(R.id.answers) as RadioGroup
        val answer1RB: RadioButton = view.findViewById(R.id.answer_1) as RadioButton
        val answer2RB: RadioButton = view.findViewById(R.id.answer_2) as RadioButton
        val answer3RB: RadioButton = view.findViewById(R.id.answer_3) as RadioButton
        val submitButton: Button = view.findViewById(R.id.validation) as Button

        // Setting the text of the elements
        questionTV.text = getString(question)
        answer1RB.text = getString(answer1)
        answer2RB.text = getString(answer2)
        answer3RB.text = getString(answer3)

        // Time
        val startTime = System.currentTimeMillis()

        // Add event to the button
        submitButton.setOnClickListener {
            // Getting the selected answer
            val selectedAnswer = radioGroup.checkedRadioButtonId

            // Time
            val endTime = System.currentTimeMillis()
            val answerTime = (endTime - startTime).toDouble()/1000

            val isAnswerCorrect: Boolean = checkTheAnswer(selectedAnswer)

            // Switch to the congrats fragment when
            // the user submit his answer
            val congratsFragment = CongratsFragment.newInstance(isAnswerCorrect, answerTime)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, congratsFragment)
            transaction.commit()
        }
        return view
    }

    /**
     * Check if the answer provided by the user is correct
     */
    private fun checkTheAnswer(answer: Int): Boolean {
        var isAnswerCorrect: Boolean = false
        when (answer) {
            R.id.answer_1 -> {
                if (correctAnswerIndex == 1) {
                    isAnswerCorrect = true
                }
            }
            R.id.answer_2 -> {
                if (correctAnswerIndex == 2) {
                    isAnswerCorrect = true
                }
            }
            R.id.answer_3 -> {
                if (correctAnswerIndex == 3) {
                    isAnswerCorrect = true
                }
            }
        }
        return isAnswerCorrect
    }

}