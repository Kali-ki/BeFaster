package com.kjnco.befaster.wifiP2p

import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast

/**
 * A PeerListListener that responds to peer list changes.
 * Extends WifiP2pManager.PeerListListener
 * @param wifiActivity The WifiDirectActivity that uses this listener
 */
class WifiPeerListener(
    private var wifiActivity : WifiDirectActivity
) : WifiP2pManager.PeerListListener {

    /**
     * Called when the peer list changes.
     */
    override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {

        // Get the list of (new) peers
        val refreshedPeers = peerList?.deviceList

        // If the list of peers has changed, update the list of peers
        if (refreshedPeers != wifiActivity.listDevice) {
            wifiActivity.listDevice.clear()
            if (refreshedPeers != null) {
                wifiActivity.listDevice.addAll(refreshedPeers)
            }
        }

        // If no peers are found, display a message
        if (wifiActivity.listDevice.isEmpty()) {
            Toast.makeText(wifiActivity, "No device found", Toast.LENGTH_SHORT).show()
        }

        // Update the UI
        wifiActivity.adapter.notifyDataSetChanged()

    }

}