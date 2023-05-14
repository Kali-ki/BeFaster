package com.kjnco.befaster.gen_know

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

/**
 * Class that starts the quiz part of our game
 */

class QuizMenu: AppCompatActivity() {

    companion object {
        // Declare the number of question to iterate on
        val numberOfQuestions: Int = 5
    }

    private lateinit var greetings: TextView
    private lateinit var rules: TextView
    private lateinit var description: TextView
    private lateinit var start: Button

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fast_quiz_intro)

        // Text of the quiz menu
        greetings = findViewById(R.id.greetings)
        rules = findViewById(R.id.rules)
        description = findViewById(R.id.description)

        // Button to start the quiz
        start = findViewById(R.id.starting)

        // Set the text of the quiz menu
        greetings.setText(R.string.quiz_greetings)
        rules.setText(R.string.quiz_rules)
        description.setText(R.string.quiz_description)
        start.setText(R.string.quiz_start)

        // Set the action of the button
        start.setOnClickListener {
            val intent = Intent(this, Quiz::class.java)
            startActivity(intent)
        }
    }

}