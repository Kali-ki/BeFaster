package com.kjnco.befaster.wifiP2p

import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import com.kjnco.befaster.R

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

        // val groupOwnerAddress : InetAddress? = wifiP2pInfo?.groupOwnerAddress

        if((wifiP2pInfo?.groupFormed == true) && wifiP2pInfo.isGroupOwner){
            wifiActivity.isHost = true
            wifiActivity.wifiServer = WifiHost(wifiActivity)
            wifiActivity.wifiServer.start()
            wifiActivity.textViewStatus.text = wifiActivity.getString(
                R.string.status, wifiActivity.getString(
                    R.string.host
                ))
        }else if(wifiP2pInfo?.groupFormed == true){
            wifiActivity.isHost = false
            wifiActivity.wifiClient = WifiClient(wifiP2pInfo.groupOwnerAddress, wifiActivity)
            wifiActivity.wifiClient.start()
            wifiActivity.textViewStatus.text = wifiActivity.getString(
                R.string.status, wifiActivity.getString(
                    R.string.client
                ))
        }
    }

}