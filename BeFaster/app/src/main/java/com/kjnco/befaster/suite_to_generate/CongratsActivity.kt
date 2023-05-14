package com.kjnco.befaster.suite_to_generate

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
        var answerTime = intent.getDoubleExtra("answerTime", 0.0)
        val numberOfArrowsRemaining = intent.getIntExtra("numberOfArrowsRemaining", 0)

        // Getting the layout elements
        val answerImage = findViewById<ImageView>(R.id.answer_image)

        // Adding the penality to the answer time
        answerTime += computingThePenality(numberOfArrowsRemaining)

        // Setting the image and the message according to the answer
        answerImage.setImageResource(R.drawable.wrong_answer)
        Toast.makeText(this, "Mauvaise réponse, tu as répondu en $answerTime s.", Toast.LENGTH_SHORT).show()

        // Getting back to the training menu
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }, 3000)

    }

    /**
     * Method to compute the penality according to the answer time and the number of arrows remaining
     */
    private fun computingThePenality(numberOfArrowsRemaining: Int): Double {
        return (numberOfArrowsRemaining * 10).toDouble()
    }

}