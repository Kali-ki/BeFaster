package com.kjnco.befaster

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kjnco.befaster.main_menu.SettingsActivity
import com.kjnco.befaster.main_menu.TrainingActivity
import com.kjnco.befaster.wifiP2p.WifiDirectActivity

/**
 * Main class that organizes all the other
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
            if(!isLocationPermissionGranted()){
                Toast.makeText(this, "BeFaster need location to run the multiplayer mode", Toast.LENGTH_LONG).show()
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
                return@setOnClickListener
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(!isNearbyWifiDevicesGranted()) {
                    Toast.makeText(this, "BeFaster need location to run the multiplayer mode", Toast.LENGTH_LONG).show()
                    requestPermissions(arrayOf(android.Manifest.permission.NEARBY_WIFI_DEVICES), 1)
                    return@setOnClickListener
                }
            }

            startActivity(Intent(this, WifiDirectActivity::class.java))
        }

        // Get the buttons
        val trainingButton: Button by lazy { findViewById(R.id.trainingMenu)}
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

        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
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
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    /**
     * Check if GPS is enabled
     */
    private fun isGpsEnabled() : Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun isLocationPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isNearbyWifiDevicesGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.NEARBY_WIFI_DEVICES) == PackageManager.PERMISSION_GRANTED
    }

}