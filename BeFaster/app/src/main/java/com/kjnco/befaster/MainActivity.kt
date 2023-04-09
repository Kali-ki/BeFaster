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

class MainActivity : AppCompatActivity() {

    private lateinit var multiplayerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun isP2pSupported() : Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)
    }

    private fun isWifiEnabled() : Boolean {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isP2pSupported
    }

    private fun isGpsEnabled() : Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}