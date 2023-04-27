package com.kjnco.befaster.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kjnco.befaster.R

class CongratsFragment: Fragment() {

    companion object{
        private const val ARG_IS_CORRECT = "isAnswerCorrect"
        private const val ARG_ANSWER_TIME = "answerTime"

        fun newInstance(isAnswerCorrect: Boolean, answerTime: Long): CongratsFragment {
            val fragment = CongratsFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_CORRECT, isAnswerCorrect)
            args.putLong(ARG_ANSWER_TIME, answerTime)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Arguments
        val isCorrect = arguments?.getBoolean("isAnswerCorrect")!!
        val answerTime = arguments?.getLong("answerTime")!!

        // View
        val view: View = inflater.inflate(R.layout.activity_congrats, container, false)

        // Elements
        val answerImage: ImageView = view.findViewById(R.id.answer_image)

        // Set the image and the Toast
        if (isCorrect) {
            answerImage.setImageResource(R.drawable.right_answer)
            Toast.makeText(context, "Bien joué, tu as répondu en $answerTime s.", Toast.LENGTH_SHORT).show()
        } else {
            answerImage.setImageResource(R.drawable.wrong_answer)
            Toast.makeText(context, "Mauvaise réponse, tu as répondu en $answerTime s.", Toast.LENGTH_SHORT).show()
        }

       Handler(Looper.getMainLooper()).postDelayed({
            (activity as? Quiz)?.setTheNextQuestion()
        }, 3000)

        return view
    }

}
