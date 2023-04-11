package com.kjnco.befaster.quiz

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kjnco.befaster.R
import java.sql.RowId

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

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 3000)

    }
}