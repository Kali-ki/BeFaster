package com.kjnco.befaster.main_menu

import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

/**
 * Class that aims to offer the key part of our game
 */

class MultiplayerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplayer)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.multi_bar)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}