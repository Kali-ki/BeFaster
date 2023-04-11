package com.kjnco.befaster.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.kjnco.befaster.R
import org.w3c.dom.Text

/**
 * A question fragment corresponding to one question
 *     and three answers.
 */
class QuestionFragment : Fragment(R.layout.fragment_question) {

    companion object {
        private const val ARG_QUESTION = "question"
        private const val ARG_ANSWER1 = "answer1"
        private const val ARG_ANSWER2 = "answer2"
        private const val ARG_ANSWER3 = "answer3"

        fun newInstance(
            question: String,
            answer1: String,
            answer2: String,
            answer3: String
        ): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putString(ARG_QUESTION, question)
            args.putString(ARG_ANSWER1, answer1)
            args.putString(ARG_ANSWER2, answer2)
            args.putString(ARG_ANSWER3, answer3)
            fragment.arguments = args
            return fragment
        }
    }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_question, container, false)

            // Getting the elements of the layout
            val question: TextView = view.findViewById(R.id.question) as TextView
            val answer1: RadioButton = view.findViewById(R.id.answer_1) as RadioButton
            val answer2: RadioButton = view.findViewById(R.id.answer_2) as RadioButton
            val answer3: RadioButton = view.findViewById(R.id.answer_3) as RadioButton

            // Setting the text of the elements
            arguments?.let{
                question.text = it.getString(ARG_QUESTION)
                answer1.text = it.getString(ARG_ANSWER1)
                answer2.text = it.getString(ARG_ANSWER2)
                answer3.text = it.getString(ARG_ANSWER3)
            }
            return view
        }
}