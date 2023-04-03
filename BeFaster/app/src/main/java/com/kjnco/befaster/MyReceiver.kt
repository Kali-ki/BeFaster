package com.kjnco.befaster

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

// This class is a BroadcastReceiver that listens for Wifi P2P events
class MyReceiver(
    private var manager : WifiP2pManager,
    private var channel : WifiP2pManager.Channel,
    private var activity : WifiDirectActivity
) : BroadcastReceiver() {

    // This method is called when a Wifi P2P event is broadcast
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){

            // Is trigger when the Wifi P2P state changes
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                Log.d("MyReceiver", "WIFI_P2P_STATE_CHANGED_ACTION")
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if(state != WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    Log.e("MyReceiver", "WIFI_P2P_STATE_CHANGED_ACTION: WIFI P2P is not enabled")
                }
            }

            // Is triggered when the list of peers changes
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d("MyReceiver", "P2P peers changed")
                activity.manager.requestPeers(channel, activity)
            }

            // Is triggered when the state of the connection changes
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                Log.d("MyReceiver", "WIFI_P2P_CONNECTION_CHANGED_ACTION")
            }

            // Is triggered when the device details change
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                Log.d("MyReceiver", "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION")
            }

        }
    }

}