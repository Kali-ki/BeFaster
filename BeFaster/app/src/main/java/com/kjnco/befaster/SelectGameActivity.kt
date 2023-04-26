package com.kjnco.befaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kjnco.befaster.wifiP2p.WifiCommunication
import kotlinx.coroutines.*

/**
 * Activity for selecting a game
 */
class SelectGameActivity : AppCompatActivity() {

    // Is this device the host
    private var isHost : Boolean = false

    // Game chosen by host
    private var gameChosen : String = ""

    // Wifi communication class
    private var wifiCommunication : WifiCommunication = WifiCommunication.getInstance()

    // UI elements
    private lateinit var statusTextView: TextView

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get game chosen by host
        val bundle : Bundle? = intent.extras
        if(bundle != null){
            isHost = bundle.getBoolean("isHost")
        }

        // Get game chosen by host
        if(isHost){
            setContentView(R.layout.activity_select_game_host)

            statusTextView = findViewById(R.id.textView_status)
            statusTextView.text = "Choose a game:"

            findViewById<Button>(R.id.buttonGame1).setOnClickListener {
                wifiCommunication.sendMsg("Game1")
            }

            findViewById<Button>(R.id.buttonGame2).setOnClickListener {
                wifiCommunication.sendMsg("Game2")
            }

            findViewById<Button>(R.id.buttonGame3).setOnClickListener {
                wifiCommunication.sendMsg("Game3")
            }

        // Wait for client to choose game
        }else{
            setContentView(R.layout.activity_select_game_client)

            statusTextView = findViewById(R.id.textView_status)
            statusTextView.text = "Waiting for host to choose game..."

            findViewById<TextView>(R.id.textView_game_chosen).text = gameChosen

            GlobalScope.launch(Dispatchers.Main){
                val res : String? = wifiCommunication.waitForMessageSuspend()
                findViewById<TextView>(R.id.textView_game_chosen).text = res
            }

        }

    }

}