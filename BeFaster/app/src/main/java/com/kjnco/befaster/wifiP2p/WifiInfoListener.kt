package com.kjnco.befaster.wifiP2p

import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager

/**
 * Custom Implementation of WifiP2pManager.ConnectionInfoListener
 */
class WifiInfoListener(
    private var wifiActivity : WifiDirectActivity
    ) : WifiP2pManager.ConnectionInfoListener {

    /**
     * Called when connection info is available.
     * Starts the server or client depending on the role of the device.
     */
    override fun onConnectionInfoAvailable(wifiP2pInfo: WifiP2pInfo?) {

        if((wifiP2pInfo?.groupFormed == true) && wifiP2pInfo.isGroupOwner){ // if host
            wifiActivity.isHost = true
            val wifiCommunication : WifiCommunication = WifiCommunication.getInstance()
            val wifiHost = WifiHost.getNewInstance(wifiCommunication)
            wifiCommunication.wifiServerClient = wifiHost
            wifiHost.start()
            WifiHost.isRunning = true
            wifiActivity.goToSelectGameActivity()
        }else if(wifiP2pInfo?.groupFormed == true){ // if client
            wifiActivity.isHost = false
            val wifiCommunication : WifiCommunication = WifiCommunication.getInstance()
            val wifiClient = WifiClient.getNewInstance(wifiP2pInfo.groupOwnerAddress, wifiCommunication)
            wifiCommunication.wifiServerClient = wifiClient
            wifiClient.start()
            WifiClient.isRunning = true
            wifiActivity.goToSelectGameActivity()
        }

    }

}