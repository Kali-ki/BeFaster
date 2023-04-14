package com.kjnco.befaster.wifiP2p

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import java.util.LinkedList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

class WifiDirectActivity : AppCompatActivity(), WifiP2pManager.ChannelListener {

    var hasAnswer: Semaphore = Semaphore(0)
    var answers : LinkedList<String> = LinkedList()

    // UI elements
    lateinit var textViewStatus : TextView
    private lateinit var buttonDiscover : Button
    private lateinit var buttonDisconnect : Button
    lateinit var progressBar: ProgressBar
    private lateinit var listView : ListView

    // List of devices
    lateinit var listDevice: ArrayList<WifiP2pDevice>
    // Adapter for the list of devices
    lateinit var adapter: DeviceP2pAdapter

    // Intent filter
    private var intentFilter : IntentFilter = IntentFilter()
    // Channel
    lateinit var channel : WifiP2pManager.Channel
    // Manager
    lateinit var manager : WifiP2pManager

    // Broadcast receiver implementation
    private lateinit var receiver : WifiReceiver

    // Wifi info listener implementation
    lateinit var wifiInfoListener: WifiInfoListener

    // Connection status
    var isConnected : Boolean = false
    var isHost : Boolean = false

    // Server and client thread
    lateinit var wifiServer : WifiHost
    lateinit var wifiClient : WifiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_direct)

        // Initialize manager and channel
        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)

        // UI
        buttonDiscover = findViewById(R.id.button_discover)
        buttonDisconnect = findViewById(R.id.button_disconnect)
        progressBar = findViewById(R.id.progressBar_discovery)
        progressBar.visibility = View.GONE
        listView = findViewById(R.id.peers_listView)
        textViewStatus = findViewById(R.id.textView_status)

        listDevice = ArrayList()
        adapter = DeviceP2pAdapter(this, listDevice, this)
        listView.adapter = this.adapter

        wifiInfoListener = WifiInfoListener(this)

        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        this.textViewStatus.text = getString(R.string.status, getString(R.string.activity_wifiDirect_notConnected))

        // Discover peers when button "Discover Peers" is clicked
        buttonDiscover.setOnClickListener {
            discoverPeers()
        }

        // Disconnect peers when button "Disconnect Peers" is clicked
        buttonDisconnect.setOnClickListener {
            disconnectPeers()
        }

        // Discover peers
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

    // --- WifiP2pManager.ChannelListener overrides -----------------------------------------------

    // Called when the channel is disconnected
    override fun onChannelDisconnected() {
        TODO()
    }

    // --- Methods --------------------------------------------------------------------------------

    /**
     * Send a message to the other device
     */
    fun sendMsg(msg : String){
        val executor : ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            if(isHost){
                wifiServer.write(msg.toByteArray())
            }else{
                wifiClient.write(msg.toByteArray())
            }
        }
    }

    fun waitForMessage() : String {
        hasAnswer.acquire()
        return answers.pop()
    }

    /**
     * Discover peers
     */
    private fun discoverPeers() {

        // Show the progress bar
        this.progressBar.visibility = View.VISIBLE

        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                Toast.makeText(applicationContext, "Discovery Started ... ", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(reasonCode: Int) {
                Toast.makeText(applicationContext, "Discovery Stating Failed ; ERROR $reasonCode", Toast.LENGTH_SHORT).show()
            }

        })
    }

    /**
     * Disconnect all peers
     */
    private fun disconnectPeers(){

            manager.removeGroup(channel, object : WifiP2pManager.ActionListener {

                override fun onSuccess() {
                    Toast.makeText(applicationContext, "Disconnected", Toast.LENGTH_SHORT).show()
                    textViewStatus.text = getString(R.string.status, getString(R.string.activity_wifiDirect_notConnected))
                }

                override fun onFailure(reasonCode: Int) {
                    Toast.makeText(applicationContext, "Disconnect Failed ; ERROR $reasonCode", Toast.LENGTH_SHORT).show()
                }

            })
    }

    /**
     * Custom adapter for the list of devices
     */
    class DeviceP2pAdapter(context : Context, devices : ArrayList<WifiP2pDevice>, wifiActivity: WifiDirectActivity)
        : ArrayAdapter<WifiP2pDevice>(context, 0, devices){

        private var manager : WifiP2pManager = wifiActivity.manager
        private var channel : WifiP2pManager.Channel = wifiActivity.channel

        // --- ArrayAdapter method ----------------------------------------------------------------

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            // Get device
            val device : WifiP2pDevice? = getItem(position)

            // Inflate view
            val view : View = convertView ?: LayoutInflater.from(context).inflate(R.layout.wifi_p2p_device, parent, false)
            // Set device name
            view.findViewById<TextView>(R.id.device_name).text = device?.deviceName

            // Connect to the device when clicked
            view.setOnClickListener {

                val config = WifiP2pConfig()
                config.deviceAddress = device?.deviceAddress
                config.wps.setup = WpsInfo.PBC
                manager.connect(channel, config, object : WifiP2pManager.ActionListener {

                    override fun onSuccess() {
                        Toast.makeText(context, "Trying to connect to ${device?.deviceName} ... ", Toast.LENGTH_SHORT).show()
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