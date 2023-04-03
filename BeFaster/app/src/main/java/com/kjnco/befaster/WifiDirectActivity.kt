package com.kjnco.befaster

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.PeerListListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast

class WifiDirectActivity : AppCompatActivity(), WifiP2pManager.ChannelListener, PeerListListener {

    // Debugging
    var tag : String = "WifiDirectActivity"

    // UI elements
    private lateinit var wifiButton: Button
    private lateinit var peersButton: Button

    // List of available peers
    private var peers = mutableListOf<WifiP2pDevice>()

    private var intentFilter : IntentFilter = IntentFilter()
    private lateinit var channel : WifiP2pManager.Channel
    lateinit var manager : WifiP2pManager
    private lateinit var receiver : MyReceiver

    private var retryChannel : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_direct)

        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        // Initialize Wi-Fi Direct
        if(!initP2p()) {
            finish()
        }

        // Set behavior for UI elements "WIFI"
        wifiButton = findViewById(R.id.wifi_button)
        wifiButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }

        // Set behavior for UI elements "DISCOVER PEERS"
        peersButton = findViewById(R.id.peers_button)
        peersButton.setOnClickListener {
            manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {

                override fun onSuccess() {
                    Log.d(tag, "Discovery Initiated")
                }

                override fun onFailure(reasonCode: Int) {
                    Log.d(tag, "Discovery Failed : $reasonCode")
                }

            })
        }

    }

    // When the activity is resumed, register the broadcast receiver
    override fun onResume() {
        super.onResume()
        receiver = MyReceiver(manager, channel, this)
        registerReceiver(receiver, intentFilter)
    }

    // When the activity is paused, unregister the broadcast receiver
    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    // Initialize Wi-Fi Direct
    private fun initP2p() : Boolean {

        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
            Log.e(tag, "Device does not support Wi-Fi Direct")
            return false
        }
        Log.d(tag, "Wi-Fi Direct is supported by this device.")

        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if(!wifiManager.isP2pSupported) {
            Log.e(tag, "Device does not support Wi-Fi Direct")
            return false
        }

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)

        Log.d(tag, "Wi-Fi Direct initialization successful.")
        return true
    }

    override fun onChannelDisconnected() {
        if(!retryChannel){
            retryChannel = true
        }else{
            Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_SHORT).show()
        }
    }

    // Callback for when peers are available
    override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {
        Log.d(tag, "onPeersAvailable() called")

        for (peer in peerList!!.deviceList) {
            Log.d(tag, "Peer: ${peer.deviceName}")
        }

        val refreshedPeers = peerList.deviceList
        if (refreshedPeers != peers) {
            peers.clear()
            if (refreshedPeers != null) {
                peers.addAll(refreshedPeers)
            }
        }

        if (peers.isEmpty()) {
            Log.d(tag, "No devices found")
        }
    }

}