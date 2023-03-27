package com.kjnco.befaster

import androidx.appcompat.app.AppCompatActivity

class MultiplayerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplayer)

        val actionBar = supportActionBar
        actionBar !!.title = "Mode multijoueur"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowCustomEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}