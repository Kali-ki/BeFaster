package com.kjnco.befaster.suite_to_generate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.TrainingActivity


class SuiteToGenerate : AppCompatActivity() {

    companion object {
        const val DURATION = 3000L
        const val DELAY = 1000L
        const val NUMBER_OF_ELEMENT = 3
        var currentIteration = 0
        const val NUMBER_OF_SEQUENCE = 3
        var currentSequence = 0
    }

    private lateinit var rules: LinearLayout
    private lateinit var beginButton : Button
    private lateinit var cancelButton: Button
    private val arrows = listOf(R.drawable.arrow_up, R.drawable.arrow_right, R.drawable.arrow_down, R.drawable.arrow_left)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suite_to_generate)

        // Linear layout init
        rules = findViewById(R.id.rules)

        // Buttons init
        beginButton = findViewById(R.id.begin)
        cancelButton = findViewById(R.id.cancel)

    }

    /**
     * Method to get back to the training menu
     */
    private fun cancelButtonEvent() {
        cancelButton.setOnClickListener {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }
    }
}