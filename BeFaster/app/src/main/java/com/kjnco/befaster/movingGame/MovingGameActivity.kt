package com.kjnco.befaster.movingGame

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.wifiP2p.WifiCommunication
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * This activity is a game where the user has to turn his phone in the direction indicated
 */
class MovingGameActivity : AppCompatActivity(), SensorEventListener {

    /**
     * Enum for the rotation direction
     */
    enum class Rotation {
        Right, Left, Center
    }

    // UI
    private lateinit var goalTextView : TextView

    // Booleans to check if the phone is turned in the right direction
    private var isTurnedLeft : Boolean = false
    private var isTurnedRight : Boolean = false
    private var isNotTurned : Boolean = false

    // Medias players
    private lateinit var mediaPlayerWin: MediaPlayer
    private lateinit var mediaPlayerLoose: MediaPlayer

    // Wifi communication class
    private var wifiCommunication : WifiCommunication = WifiCommunication.getInstance()
    private var isMultiplayer : Boolean = false
    private var isHost : Boolean = false

    // Sensors
    private lateinit var sensorManager: SensorManager

    // Rotation sensor
    private lateinit var rotation: Sensor

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mooving_game)

        // UI
        goalTextView = findViewById(R.id.goalTextView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        // Get if the game is multiplayer and if the user is the host
        val bundle : Bundle? = intent.extras
        if(bundle != null){
            isMultiplayer = bundle.getBoolean("isMultiplayer")
            if(isMultiplayer){
                isHost = bundle.getBoolean("isHost")
            }
        }

        // Init media players with right sound
        mediaPlayerWin = MediaPlayer.create(this, R.raw.victory)
        mediaPlayerLoose = MediaPlayer.create(this, R.raw.defeat)

        // Start the game in a coroutine
        GlobalScope.launch(Dispatchers.IO){
            var elapsed = measureTimeMillis { // Measure time to complete the game
                for (i in 0..15) { // 15 rounds
                    when ((0..2).random()) { // Random direction

                        // 0 -> Left
                        0 -> {
                            goalTextView.text = Rotation.Left.name
                            while (!isTurnedLeft) {}
                        }

                        // 1 -> Right
                        1 -> {
                            goalTextView.text = Rotation.Right.name
                            while (!isTurnedRight) {}
                        }

                        // 2 -> Center
                        2 -> {
                            goalTextView.text = Rotation.Center.name
                            while (!isNotTurned) {}
                        }

                    }
                }
            }
            elapsed /= 1000 // Convert to seconds
            goalTextView.text = getString(R.string.moving_game_time, elapsed.toString())

            // If the game is multiplayer, send the time to the opponent and wait for his time
            if(isMultiplayer){
                var hasWon = 0
                if(isHost){
                    val elapsedOpponent = wifiCommunication.waitForMessageSuspendWithoutTimeout()?.toLong()
                    if(elapsedOpponent!! > elapsed){
                        hasWon = 1
                        wifiCommunication.sendMsg("0")
                    }else{
                        wifiCommunication.sendMsg("1")
                    }
                }else{
                    wifiCommunication.sendMsg(elapsed.toString())
                    val result = wifiCommunication.waitForMessageSuspend()
                    hasWon = result!!.toInt()
                }
                if(hasWon == 1){
                    goalTextView.text = getString(R.string.win)
                    mediaPlayerWin.start()
                }else{
                    goalTextView.text = getString(R.string.loose)
                    mediaPlayerLoose.start()
                }
            }

            // Display result 3 seconds
            Thread.sleep(3000)

            // Finish activity
            finish()

        }

    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_ROTATION_VECTOR){

            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(
                rotationMatrix, event.values
            )
            val remappedRotationMatrix = FloatArray(16)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                remappedRotationMatrix
            )
            val orientations = FloatArray(3)
            SensorManager.getOrientation(remappedRotationMatrix, orientations)
            for (i in 0..2) {
                orientations[i] = Math.toDegrees(orientations[i].toDouble()).toFloat()
            }

            if(orientations[2] > 45){
                window.decorView.setBackgroundColor(Color.RED)
                isTurnedLeft = false
                isTurnedRight = true
                isNotTurned = false
            }else if(orientations[2] < -45){
                window.decorView.setBackgroundColor(Color.BLUE)
                isTurnedLeft = true
                isTurnedRight = false
                isNotTurned = false
            }else{
                window.decorView.setBackgroundColor(Color.WHITE)
                isTurnedLeft = false
                isTurnedRight = false
                isNotTurned = true
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        rotation.also { rotation ->
            sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}