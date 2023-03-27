package com.kjnco.befaster.main_menu

import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

/**
 * Class that aims to tune the application settings
 *       to add more challegens to the game
 */
class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings_bar)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}