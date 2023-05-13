package com.kjnco.befaster.self_generated_suite

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.TrainingActivity

class CongratsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congrats)

        // Getting the data passed by the previous activity
        val isAnswerCorrect = intent.getBooleanExtra("isAnswerCorrect", false)
        val time = intent.getLongExtra("answerTime", 0L)
        val answerTime: Double = time.toDouble() / 1000.0

        // Getting the layout elements
        val answerImage = findViewById<ImageView>(R.id.answer_image)

        // Setting the image and the message according to the answer
        if (isAnswerCorrect) {
            answerImage.setImageResource(R.drawable.right_answer)
            Toast.makeText(this, "Bien joué, tu as répondu en $answerTime s.", Toast.LENGTH_SHORT).show()
        }else {
            answerImage.setImageResource(R.drawable.wrong_answer)
            Toast.makeText(this, "Mauvaise réponse, tu as répondu en $answerTime s.", Toast.LENGTH_SHORT).show()
        }

        // Iterating over the activity
        SelfGenerated.currentSequence++
        if (SelfGenerated.currentSequence < SelfGenerated.NUMBER_OF_SEQUENCE){
            SelfGenerated.currentIteration = 0
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, SelfGenerated::class.java)
                startActivity(intent)
            }, 3000)
        }else {
            SelfGenerated.currentIteration = 0
            SelfGenerated.currentSequence = 0
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, TrainingActivity::class.java)
                startActivity(intent)
            }, 3000)
        }

    }

}