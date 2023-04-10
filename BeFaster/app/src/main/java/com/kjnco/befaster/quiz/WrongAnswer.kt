package com.kjnco.befaster.quiz

import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class WrongAnswer: AppCompatActivity() {

    var answerTime: Long = 0L

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inter_question_page)

        // Getting the Image View
        val imageView: ImageView by lazy { findViewById(R.id.answer_image) }

        // Setting the Image View
        imageView.setImageResource(R.drawable.wrong_answer)

        // Make a Toast of congratulations with time imported from Q1 class
        Toast.makeText(this,"Dommage ! Tu as échoué et répondu en " + answerTime + " secondes.", Toast.LENGTH_LONG).show()
    }
}