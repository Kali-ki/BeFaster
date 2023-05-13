package com.kjnco.befaster.self_generated_suite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.TrainingActivity

class SelfGenerated : AppCompatActivity() {


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
    var listOfArrows = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suite_self_generated)

        // Linear layout init
        rules = findViewById(R.id.rules)

        // Skip the rules if the user has already done a sequence
        if (currentSequence >= 1) {
            rules.visibility = LinearLayout.INVISIBLE
            continueSequence()
        }

        // Customize the rules
        customizeRules()

        // Buttons init
        beginButton = findViewById(R.id.begin)
        cancelButton = findViewById(R.id.cancel)

        // Buttons events
        beginButtonEvent()
        cancelButtonEvent()

    }

    /**
     * Method to continue the sequence without recalling the rules
     */
    private fun continueSequence() {
        val arrow = arrows.random()
        listOfArrows.add(arrow)
        val fragment = ElementFragment.newInstance(arrow)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Method to customize the text of the rules
     *     according to the number of sequences
     */
    @SuppressLint("SetTextI18n")
    private fun customizeRules() {
        val rulesTextToCustomize = findViewById<TextView>(R.id.rules_1)
        rulesTextToCustomize.text = getText(R.string.self_gen_rule_1_1_fr).toString() + " " + NUMBER_OF_ELEMENT + " " + getText(R.string.self_gen_rule_1_2_fr).toString() + " " + NUMBER_OF_SEQUENCE + " " + getText(R.string.self_gen_rule_1_3_fr).toString()
    }

    /**
     * Method to add event on the begin button
     */
    private fun beginButtonEvent() {
        beginButton.setOnClickListener() {
            rules.visibility = LinearLayout.INVISIBLE
            val arrow = arrows.random()
            listOfArrows.add(arrow)
            val fragment = ElementFragment.newInstance(arrow)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    /**
     * Method to add event on the cancel button
     */
    private fun cancelButtonEvent() {
        cancelButton.setOnClickListener() {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to go from a fragment to another
     */
    fun goToNextElement() {
        currentIteration++
        if (currentIteration < NUMBER_OF_ELEMENT) {
            val arrow = arrows.random()
            listOfArrows.add(arrow)
            val fragment = ElementFragment.newInstance(arrow)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }else {
            val intent = Intent(this, RestitutionActivity::class.java)
            val arrayOfArrows = ArrayList<Int>(listOfArrows)
            listOfArrows.clear()
            intent.putIntegerArrayListExtra("listOfArrows", arrayOfArrows)
            startActivity(intent)
        }
    }

}