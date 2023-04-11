package com.kjnco.befaster.quiz

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.sql.RowId

class RightAnswer: Fragment(){

    companion object {
        private const val ARG_ANSWER_TIME = "answerTime"

        fun newInstance(answerTime: Long): RightAnswer {
            val fragment = RightAnswer()
            val args = Bundle()
            args.putLong(ARG_ANSWER_TIME, answerTime)
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
        val view = inflater.inflate(R.layout.inter_question_page, container, false)

        //Getting the Image View
        val imageView: ImageView = view.findViewById(R.id.answer_image) as ImageView

        // Setting the image
        imageView.setImageResource(R.drawable.right_answer)

        // Retrieve the time from the previous activity
        val answerTime = arguments?.getLong("answerTime", 0L)?:0

        // Make a Toast of congratulations with time imported from Q1 class
        Toast.makeText(context, "Félicitations ! Réponse en " + answerTime +" secondes.", Toast.LENGTH_LONG).show()

        return view
    }

}