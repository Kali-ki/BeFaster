package com.kjnco.befaster

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class WifiDirectActivity : AppCompatActivity(), WifiP2pManager.ChannelListener {

    // Debugging
    private var tag : String = "WifiDirectActivity"

    // UI
    private lateinit var buttonDiscover : Button
    lateinit var progressBar: ProgressBar
    private lateinit var listView : ListView

    lateinit var listDevice: ArrayList<WifiP2pDevice>
    lateinit var adapter: DeviceP2pAdapter

    private var intentFilter : IntentFilter = IntentFilter()
    lateinit var channel : WifiP2pManager.Channel
    lateinit var manager : WifiP2pManager
    private lateinit var receiver : WifiReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_direct)

        // Initialize manager and channel
        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)

        // UI
        this.buttonDiscover = findViewById(R.id.button_discover)
        this.progressBar = findViewById(R.id.progressBar_discovery)
        this.progressBar.visibility = View.INVISIBLE
        this.listView = findViewById(R.id.peers_listView)

        this.listDevice = ArrayList()
        this.adapter = DeviceP2pAdapter(this, listDevice, this)
        listView.adapter = this.adapter

        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        buttonDiscover.setOnClickListener {
            discoverPeers()
        }

        discoverPeers()

    }

    // When the activity is resumed, register the broadcast receiver
    override fun onResume() {
        super.onResume()
        receiver = WifiReceiver(this)
        registerReceiver(receiver, intentFilter)
    }

    // When the activity is paused, unregister the broadcast receiver
    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    // Called when the channel is disconnected
    override fun onChannelDisconnected() {
        Log.d(tag, "Channel disconnected")
    }

    private fun discoverPeers() {

        this.progressBar.visibility = View.VISIBLE

        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                Toast.makeText(applicationContext, "Discovery Initiated", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(reasonCode: Int) {
                Toast.makeText(applicationContext, "Discovery Failed : $reasonCode", Toast.LENGTH_SHORT).show()
            }

        })
    }

    class DeviceP2pAdapter(context : Context, devices : ArrayList<WifiP2pDevice>, wifiActivity: WifiDirectActivity)
        : ArrayAdapter<WifiP2pDevice>(context, 0, devices){

        private var manager : WifiP2pManager = wifiActivity.manager
        private var channel : WifiP2pManager.Channel = wifiActivity.channel

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val device : WifiP2pDevice? = getItem(position)

            val view : View = convertView ?: LayoutInflater.from(context).inflate(R.layout.wifi_p2p_device, parent, false)
            view.findViewById<TextView>(R.id.device_name).text = device?.deviceName

            // Connect to device
            view.setOnClickListener {

                val config = WifiP2pConfig()
                config.deviceAddress = device?.deviceAddress
                config.wps.setup = WpsInfo.PBC
                manager.connect(channel, config, object : WifiP2pManager.ActionListener {

                    override fun onSuccess() {
                        Toast.makeText(context, "Connected to ${device?.deviceName}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(reason: Int) {
                        Toast.makeText(context, "Failed to connect to ${device?.deviceName}", Toast.LENGTH_SHORT).show()
                    }

                })
            }

            // Long click to show device address
            view.setOnLongClickListener {
                Toast.makeText(context, "Long clicked on ${device?.deviceAddress}", Toast.LENGTH_SHORT).show()
                true
            }

            return view
        }

    }

}