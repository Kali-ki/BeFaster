package com.kjnco.befaster

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

/**
 * Main Activity of the app
 */
class MainActivity : AppCompatActivity() {

    // UI element
    private lateinit var multiplayerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up Button Multiplayer
        multiplayerButton = findViewById(R.id.multiplayer_button)
        multiplayerButton.setOnClickListener {
            if(!isP2pSupported()) {
                Toast.makeText(this, "P2P not supported", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isWifiEnabled()) {
                Toast.makeText(this, "Start Wifi and retry", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isGpsEnabled()) {
                Toast.makeText(this, "Start GPS and retry", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, WifiDirectActivity::class.java))
        }

    }

    /**
     * Check if P2P is supported
     */
    private fun isP2pSupported() : Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)
    }

    /**
     * Check if Wifi is enabled
     */
    private fun isWifiEnabled() : Boolean {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    /**
     * Check if GPS is enabled
     */
    private fun isGpsEnabled() : Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}