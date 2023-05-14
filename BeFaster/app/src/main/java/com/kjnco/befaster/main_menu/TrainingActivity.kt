package com.kjnco.befaster.main_menu

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.MainActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.movingGame.MovingGameActivity
import com.kjnco.befaster.quiz.QuizMenu
import com.kjnco.befaster.self_generated_suite.SelfGenerated
import com.kjnco.befaster.suite_to_generate.SuiteToGenerate

/**
 * Class that aims to offer a mode in solo for training
 */
class TrainingActivity: AppCompatActivity() {

    private lateinit var genCultureQuiz: Button
    private lateinit var wordQuiz: Button
    private lateinit var fillTheRest: Button
    private lateinit var selfGenerated: Button
    private lateinit var suiteToGenerate: Button
    private lateinit var labyrinth: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        // Buttons init
        initButtons()

        // Set the action of the buttons
        genCultureQuizButtonEvent()
        wordQuizButtonEvent()
        fillTheRestButtonEvent()
        selfGeneratedButtonEvent()
        suiteToGenerateButtonEvent()
        movingGameButtonEvent()
        cancelButtonEvent()
    }

    /**
     * Method to add an event to the genCultureQuiz button
     */
    private fun genCultureQuizButtonEvent() {
        genCultureQuiz.setOnClickListener {
            val intent = Intent(this, QuizMenu::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the wordQuiz button
     */
    private fun wordQuizButtonEvent() {
        wordQuiz.setOnClickListener {
            val intent = Intent(this, QuizMenu::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the fillTheRest button
     */
    private fun fillTheRestButtonEvent() {
        fillTheRest.setOnClickListener {
            val intent = Intent(this, QuizMenu::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the selfGenerated button
     */
    private fun selfGeneratedButtonEvent() {
        selfGenerated.setOnClickListener {
            val intent = Intent(this, SelfGenerated::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the suiteToGenerate button
     */
    private fun suiteToGenerateButtonEvent() {
        suiteToGenerate.setOnClickListener {
            val intent = Intent(this, SuiteToGenerate::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the labyrinth button
     */
    private fun movingGameButtonEvent() {
        labyrinth.setOnClickListener {
            val intent = Intent(this, MovingGameActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to add an event to the cancel button
     */
    private fun cancelButtonEvent() {
        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to initialize the buttons
     */
    private fun initButtons() {
        genCultureQuiz = findViewById(R.id.gen_culture_quiz)
        wordQuiz = findViewById(R.id.word_quiz)
        fillTheRest = findViewById(R.id.fill_the_rest)
        selfGenerated = findViewById(R.id.self_generated)
        suiteToGenerate = findViewById(R.id.suite_to_generate)
        labyrinth = findViewById(R.id.labyrinth)
        cancelButton = findViewById(R.id.cancel)
    }
}