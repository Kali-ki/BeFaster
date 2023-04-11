package com.kjnco.befaster.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.MultiplayerActivity
import com.kjnco.befaster.main_menu.SettingsActivity
import com.kjnco.befaster.main_menu.TrainingActivity

/**
 * Main class that organizes all the other
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the buttons
        val trainingButton: Button by lazy { findViewById(R.id.trainingMenu)}
        val multiplayerButton: Button by lazy { findViewById(R.id.multiMenu)}
        val settingsButton: Button by lazy { findViewById(R.id.settingsMenu)}

        // Set the text of the buttons
        trainingButton.setText(R.string.training_mode)
        multiplayerButton.setText(R.string.multi_mode)
        settingsButton.setText(R.string.settings)

        // Set the action of the buttons
        trainingButton.setOnClickListener {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }

        multiplayerButton.setOnClickListener{
            val intent = Intent(this, MultiplayerActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }

}