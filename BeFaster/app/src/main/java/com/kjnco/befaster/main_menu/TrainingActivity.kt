package com.kjnco.befaster.main_menu

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.MainActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.quiz.QuizMenu
import com.kjnco.befaster.self_generated_suite.SelfGenerated

/**
 * Class that aims to offer a mode in solo for training
 */
class TrainingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        // Get the element of the training activity
        val description1: TextView by lazy { findViewById(R.id.description_1)}
        val description2: TextView by lazy { findViewById(R.id.description_2)}
        val description3: TextView by lazy { findViewById(R.id.description_3)}
        val fastQuiz: Button by lazy { findViewById(R.id.fast_quiz)}
        val selfGenerated: Button by lazy { findViewById(R.id.self_generated)}
        val cancelButton: Button by lazy {findViewById(R.id.cancel)}

        // Set the text of the buttons
        description1.setText(R.string.training_description_1)
        description2.setText(R.string.training_description_2)
        description3.setText(R.string.training_description_3)
        fastQuiz.setText(R.string.quiz)
        selfGenerated.setText(R.string.self_generated_fr)
        cancelButton.setText(R.string.cancel)

        // Set the action of the buttons
        fastQuiz.setOnClickListener{
            val intent = Intent(this, QuizMenu::class.java)
            startActivity(intent)
        }

        selfGenerated.setOnClickListener{
            val intent = Intent(this, SelfGenerated::class.java)
            startActivity(intent)
        }

        cancelButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}