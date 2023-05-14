package com.kjnco.befaster.suite_to_generate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.TrainingActivity


class SuiteToGenerate : AppCompatActivity() {

    companion object {
        val arrow_pictures = ArrayList<Int>()
        const val DURATION = 3000L
        const val DELAY = 1000L
    }

    init {
        arrow_pictures.add(R.drawable.blue_arrow_up)
        arrow_pictures.add(R.drawable.blue_arrow_down)
        arrow_pictures.add(R.drawable.blue_arrow_left)
        arrow_pictures.add(R.drawable.blue_arrow_right)
    }

    private lateinit var rules: LinearLayout
    private lateinit var beginButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suite_to_generate)

        // Linear layout init
        rules = findViewById(R.id.rules)

        // Buttons init
        beginButton = findViewById(R.id.begin)
        cancelButton = findViewById(R.id.cancel)

        // Buttons action
        cancelButtonEvent()
        beginButtonEvent()

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

    /**
     * Method to begin the game with one arrow to draw
     *     it will display a blank screen to draw
     */
    private fun beginButtonEvent() {
        beginButton.setOnClickListener {
            rules.visibility = LinearLayout.INVISIBLE
            val intent = Intent(this, ExtraArrowActivity::class.java)
            intent.putStringArrayListExtra("listOfArrows", ArrayList<String>())
            intent.putExtra("answerTime", 0.0)
            startActivity(intent)
        }
    }
}