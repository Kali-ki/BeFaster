package com.kjnco.befaster.wifiP2p

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import com.kjnco.befaster.wifiP2p.WifiDirectActivity

/**
 * A PeerListListener that responds to peer list changes.
 * Extends WifiP2pManager.PeerListListener
 */
class WifiPeerListener(
    private var wifiActivity : WifiDirectActivity
)
    : WifiP2pManager.PeerListListener {

    private var peers : ArrayList<WifiP2pDevice> = wifiActivity.listDevice
    private var adapter : WifiDirectActivity.DeviceP2pAdapter = wifiActivity.adapter

    // --- WifiP2pManager.PeerListListener overrides ----------------------------------------------

    override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {

        // Get the list of (new) peers
        val refreshedPeers = peerList?.deviceList

        if (refreshedPeers != peers) {
            peers.clear()
            if (refreshedPeers != null) {
                peers.addAll(refreshedPeers)
            }
        }

        if (peers.isEmpty()) {
            Toast.makeText(wifiActivity, "No device found", Toast.LENGTH_SHORT).show()
        }

        // Update the UI
        adapter.notifyDataSetChanged()

    }

}