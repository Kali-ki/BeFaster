package com.kjnco.befaster.quiz

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kjnco.befaster.R

class WrongAnswer: Fragment() {


    companion object {
        private const val ARG_ANSWER_TIME = "answerTime"

        fun newInstance(answerTime: Long): WrongAnswer {
            val fragment = WrongAnswer()
            val args = Bundle()
            args.putLong(ARG_ANSWER_TIME, answerTime)
            fragment.arguments = args
            return fragment
        }
    }

    // Attributes

    var answerTime: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.inter_question_page, container, false)

        // Getting the Image View
        val imageView: ImageView = view.findViewById(R.id.answer_image) as ImageView

        // Setting the Image View
        imageView.setImageResource(R.drawable.wrong_answer)

        // Retrieve the time from the previous activity
        val answerTime = arguments?.getLong("answerTime", 0L)?:0

        Toast.makeText(context,"Dommage ! Tu as échoué et répondu en " + answerTime + " secondes.", Toast.LENGTH_LONG).show()

        return view
    }
}