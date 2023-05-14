package com.kjnco.befaster.anagram

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.TrainingActivity

class AnagramActivity : AppCompatActivity() {

    companion object {
        var numberOfAnagrams = 3
    }
    private lateinit var rules_1: TextView
    private lateinit var rules_2: TextView
    private lateinit var rules_3: TextView
    private lateinit var beginButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anagram)

        // Init text views
        rules_1 = findViewById(R.id.rules_1)
        rules_2 = findViewById(R.id.rules_2)
        rules_3 = findViewById(R.id.rules_3)

        // Set texts
        rules_1.text = getString(R.string.anagram_rules_1_fr) +" "+ numberOfAnagrams
        rules_2.setText(R.string.anagram_rules_2_fr)
        rules_3.setText(R.string.anagram_rules_3_fr)

        // Init buttons
        beginButton = findViewById(R.id.begin)
        cancelButton = findViewById(R.id.cancel)

        // Set the action of the buttons
        beginButtonEvent()
        cancelButtonEvent()

    }

    /**
     * Method to add an event to the begin button
     */
    private fun beginButtonEvent() {
        beginButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the cancel button
     */
    private fun cancelButtonEvent() {
        cancelButton.setOnClickListener {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }
    }
}