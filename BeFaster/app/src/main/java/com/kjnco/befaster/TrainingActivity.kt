package com.kjnco.befaster

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class TrainingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val actionBar = supportActionBar
        actionBar !!.title = "Mode d'entraînement"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowCustomEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}