package com.kjnco.befaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trainingButton: Button by lazy { findViewById(R.id.trainingMenu)}
        val multiplayerButton: Button by lazy { findViewById(R.id.multiMenu)}
        val settingsButton: Button by lazy { findViewById(R.id.settingsMenu)}

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