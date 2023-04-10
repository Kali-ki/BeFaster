package com.kjnco.befaster.quiz

import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class RightAnswer: AppCompatActivity(){

    var answerTime: Long = 0L

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inter_question_page)

        //Getting the Image View
        val imageView: ImageView by lazy { findViewById(R.id.answer_image) }

        // Setting the image
        imageView.setImageResource(R.drawable.right_answer)

        // Retrieve the time from the previous activity
        answerTime = intent.getLongExtra("answerTime", 0L)

        // Make a Toast of congratulations with time imported from Q1 class
        Toast.makeText(this, "Félicitations ! Réponse en " + answerTime +" secondes.", Toast.LENGTH_LONG).show()
    }
}