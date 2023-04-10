package com.kjnco.befaster

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Build.VERSION
import android.view.View
import android.widget.Toast

/**
 * This class is a BroadcastReceiver that listens for Wifi P2P events
 * Extends BroadcastReceiver
 */
class WifiReceiver(
    private var wifiActivity: WifiDirectActivity
) : BroadcastReceiver() {

    private var manager = wifiActivity.manager
    private var channel = wifiActivity.channel
    private var wifiInfoListener = wifiActivity.wifiInfoListener
    private var progressbar = wifiActivity.progressBar
    private var isConnected = wifiActivity.isConnected

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){

            // Indicates whether Wi-Fi P2P is enabled or not
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if(state != WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    Toast.makeText(wifiActivity, "Wifi P2P is not enabled", Toast.LENGTH_SHORT).show()
                }
            }

            // Indicates that available peer list has changed
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Request the list of peers from the wifi p2p manager
                manager.requestPeers(channel, WifiPeerListener(wifiActivity))
                // Hide the progress bar
                this.progressbar.visibility = View.GONE
            }

            // Indicates the state of Wi-Fi P2P connectivity has changed
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {

                // API 33 -> Tiramisu -> Android 13

                @Suppress("DEPRECATION" )
                val networkInfo : NetworkInfo?
                if(VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    @Suppress("DEPRECATION" )
                    networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO, NetworkInfo::class.java)
                }else{
                    @Suppress("DEPRECATION" )
                    networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO)
                }

                @Suppress("DEPRECATION" )
                if(networkInfo?.isConnected == true){
                    Toast.makeText(wifiActivity, "Connection Succeed", Toast.LENGTH_SHORT).show()
                    manager.requestConnectionInfo(channel, wifiInfoListener)
                    isConnected = true
                }

            }

            // Indicates this device's configuration details have changed
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                // Do nothing
            }

        }
    }

}