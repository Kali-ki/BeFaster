package com.kjnco.befaster

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast

class WifiPeerListener(
    private var wifiActivity : WifiDirectActivity)
    : WifiP2pManager.PeerListListener {

    private var peers : ArrayList<WifiP2pDevice> = wifiActivity.listDevice
    private var adapter : WifiDirectActivity.DeviceP2pAdapter = wifiActivity.adapter

    // Debugging
    var tag : String = "WifiPeerListener"

    // --- WifiP2pManager.PeerListListener overrides ----------------------------------------------

    override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {

        val refreshedPeers = peerList?.deviceList
        if (refreshedPeers != peers) {
            peers.clear()
            if (refreshedPeers != null) {
                peers.addAll(refreshedPeers)
            }
        }

        if (peers.isEmpty()) {
            Toast.makeText(wifiActivity, "No devices found", Toast.LENGTH_SHORT).show()
        }

        adapter.notifyDataSetChanged()
    }

}