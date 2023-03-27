package com.kjnco.befaster.quiz

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

/**
 * Class that starts the quiz part of our game
 */

class QuizMenu: AppCompatActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fast_quiz_intro)

        // Text of the quiz menu
        val greetings: TextView by lazy { findViewById(R.id.greetings) }
        val rules: TextView by lazy { findViewById(R.id.rules) }
        val description: TextView by lazy { findViewById(R.id.description) }
        // Button to start the quiz
        val start: Button by lazy {findViewById(R.id.starting)}

        // Set the text of the quiz menu
        greetings.setText(R.string.quiz_greetings)
        rules.setText(R.string.quiz_rules)
        description.setText(R.string.quiz_description)
        start.setText(R.string.quiz_start)

        // Set the action of the button
        start.setOnClickListener {
            val intent = Intent(this, Q1::class.java)
            startActivity(intent)
        }

        // Put in place action bar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.quiz_bar)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}