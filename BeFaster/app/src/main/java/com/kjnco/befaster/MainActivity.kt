package com.kjnco.befaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Go to WifiDirectActivity
        startActivity(Intent(this, WifiDirectActivity::class.java))

    }

}