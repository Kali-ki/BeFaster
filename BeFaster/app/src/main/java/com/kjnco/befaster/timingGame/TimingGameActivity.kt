package com.kjnco.befaster.timingGame

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.kjnco.befaster.R
import com.kjnco.befaster.wifiP2p.WifiCommunication
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 * This activity is a game where the user has to shake his phone just before the timer ends.
 */
class TimingGameActivity : AppCompatActivity(), SensorEventListener {

    // UI
    private lateinit var stepTextView: TextView

    // Wifi communication class
    private var wifiCommunication : WifiCommunication = WifiCommunication.getInstance()
    private var isMultiplayer : Boolean = false
    private var isHost : Boolean = false

    // Medias players
    private lateinit var mediaPlayerWin: MediaPlayer
    private lateinit var mediaPlayerLoose: MediaPlayer

    // Sensors
    private lateinit var sensorManager: SensorManager

    //  sensor
    private lateinit var accSensor: Sensor

    // Acceleration
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    private var isRunning : Boolean = false
    private var asFinished : Boolean = false

    // CountDownTimer
    private lateinit var cTimer1 : CountDownTimer
    private lateinit var cTimer2 : CountDownTimer
    private lateinit var cTimer3 : CountDownTimer

    // Actual CountDownTimer
    private var actualTimer : Int = 0

    // Booleans to check if the phone was shaken before the timer end
    private var hasStoppedTooLate : Boolean = false

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_game)

        // UI
        stepTextView = findViewById(R.id.step_game_text_view)

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

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        var t1: Int
        var t2: Int
        var t3: Int

        val scores = mutableListOf<Int>()

        GlobalScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO){
                if ((isMultiplayer && isHost) || !isMultiplayer) {
                    t1 = (5..15).random() * 1000
                    t2 = (5..15).random() * 1000
                    t3 = (5..15).random() * 1000

                    if (isMultiplayer) {
                        val timesString = "$t1 $t2 $t3"
                        wifiCommunication.sendMsg(timesString)
                    }

                } else {
                    val res = wifiCommunication.waitForMessageSuspend()
                    val times = res!!.split(" ")
                    t1 = times[0].toInt()
                    t2 = times[1].toInt()
                    t3 = times[2].toInt()
                }

            }

            cTimer1 = createCountDown(t1.toLong())
            cTimer2 = createCountDown(t2.toLong())
            cTimer3 = createCountDown(t3.toLong())

            val cTimers = listOf(cTimer1, cTimer2, cTimer3)

            withContext(Dispatchers.IO){
                for (i in (0..2)){
                    actualTimer = i
                    cTimers[i].start()
                    isRunning = true
                    val elapsed = measureTimeMillis{
                        while(!asFinished){}
                    }
                    if(hasStoppedTooLate){
                        scores.add(0)
                        hasStoppedTooLate = false
                    }else{
                        scores.add(elapsed.toInt())
                    }
                    isRunning = false
                    asFinished = false
                    Thread.sleep(2000)
                }
            }

            stepTextView.text = getString(R.string.scores, scores[0].toString(), scores[1].toString(), scores[2].toString())

            withContext(Dispatchers.IO){
                if(isMultiplayer){
                    val totalScore = scores[0] + scores[1] + scores[2]
                    var hasWon = 0
                    if(isHost){
                        val opponentScore = wifiCommunication.waitForMessageSuspendWithoutTimeout()?.toInt()
                        if(totalScore > opponentScore!!){
                            hasWon = 1
                            wifiCommunication.sendMsg("0")
                        }else{
                            wifiCommunication.sendMsg("1")
                        }
                    }else{
                        wifiCommunication.sendMsg(totalScore.toString())
                        hasWon = wifiCommunication.waitForMessageSuspend()?.toInt()!!
                    }

                    withContext(Dispatchers.Main){
                        if(hasWon == 1){
                            mediaPlayerWin.start()
                            stepTextView.text = getString(R.string.win)
                        }else{
                            mediaPlayerLoose.start()
                            stepTextView.text = getString(R.string.loose)
                        }
                    }
                }
                Thread.sleep(5000)
                finish()
            }

        }

    }

    override fun onResume() {
        super.onResume()

        accSensor.also { step ->
            sensorManager.registerListener(this, step, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {

        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = kotlin.math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            if (acceleration > 15) {
                if(isRunning){

                    when(actualTimer){
                        0 -> cTimer1.cancel()
                        1 -> cTimer2.cancel()
                        2 -> cTimer3.cancel()
                    }

                    asFinished = true
                }
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun createCountDown(totalTime : Long) : CountDownTimer{
        return object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if(millisUntilFinished > totalTime-3000){
                    stepTextView.text = (millisUntilFinished / 1000).toString()
                }else{
                    stepTextView.text = getString(R.string.shake_instruction)
                }
            }
            override fun onFinish() {
                asFinished = true
                hasStoppedTooLate = true
            }
        }
    }

}
