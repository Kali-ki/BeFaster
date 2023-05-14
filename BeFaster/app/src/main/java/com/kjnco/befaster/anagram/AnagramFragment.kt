package com.kjnco.befaster.anagram

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

class AnagramFragment : Fragment() {

    // Anagram IDs
    private var anagram: Int = 0
    private var oddOneOut: Int = 0
    private var answer1: Int = 0
    private var answer2: Int = 0
    private var answer3: Int = 0

    private var correctAnswerIndex: Int = 0

    companion object {
        private const val ARG_ANAGRAM = "anagram"
        private const val ARG_ANSWER_1 = "answer1"
        private const val ARG_ANSWER_2 = "answer2"
        private const val ARG_ANSWER_3 = "answer3"
        private const val ARG_ODD_ONE_OUT = "oddOneOut"

        fun newInstance(anagram: Int, answer1: Int, answer2: Int, answer3: Int, oddOneOut: Int): AnagramFragment {
            val fragment = AnagramFragment()
            val args = Bundle()
            args.putInt(AnagramFragment.ARG_ANAGRAM, anagram)
            args.putInt(AnagramFragment.ARG_ANSWER_1, answer1)
            args.putInt(AnagramFragment.ARG_ANSWER_2, answer2)
            args.putInt(AnagramFragment.ARG_ANSWER_3, answer3)
            args.putInt(AnagramFragment.ARG_ODD_ONE_OUT, oddOneOut)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Getting the arguments
        anagram = arguments?.getInt("anagram")!!
        oddOneOut = arguments?.getInt("oddOneOut")!!
        answer1 = arguments?.getInt("answer1")!!
        answer2 = arguments?.getInt("answer2")!!
        answer3 = arguments?.getInt("answer3")!!

        // Determining the index of the correct answer
        correctAnswerIndex = when (oddOneOut) {
            answer1 -> 1
            answer2 -> 2
            answer3 -> 3
            else -> 0
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_anagram, container, false)

        // Getting the elements of the layout
        val questionTV: TextView = view.findViewById(R.id.question) as TextView
        val radioGroup: RadioGroup = view.findViewById(R.id.answers) as RadioGroup
        val answer1RB: RadioButton = view.findViewById(R.id.answer_1) as RadioButton
        val answer2RB: RadioButton = view.findViewById(R.id.answer_2) as RadioButton
        val answer3RB: RadioButton = view.findViewById(R.id.answer_3) as RadioButton
        val submitButton: Button = view.findViewById(R.id.validation) as Button

        // Setting the text of the elements
        questionTV.text = getString(anagram)
        answer1RB.text = getString(answer1)
        answer2RB.text = getString(answer2)
        answer3RB.text = getString(answer3)

        // Time
        val startTime = System.currentTimeMillis()

        // Add event to the button
        submitAnswer(submitButton, radioGroup, startTime)

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

    /**
     * Submit the answer provided by the user
     */
    private fun submitAnswer(submitButton: Button, radioGroup: RadioGroup, startTime: Long) {
        submitButton.setOnClickListener {
            // Getting the selected answer
            val selectedAnswer = radioGroup.checkedRadioButtonId

            // Time
            val endTime = System.currentTimeMillis()
            val answerTime = (endTime - startTime).toDouble() / 1000

            val isAnswerCorrect: Boolean = checkTheAnswer(selectedAnswer)

            // Switch to the congrats fragment when
            // the user submit his answer
            val answerFragment = AnswerFragment.newInstance(isAnswerCorrect, answerTime)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, answerFragment)
            transaction.commit()
        }
    }
}