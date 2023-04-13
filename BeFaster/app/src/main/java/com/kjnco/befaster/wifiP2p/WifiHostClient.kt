package com.kjnco.befaster.wifiP2p

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class WifiHostClient(private var wifiActivity : WifiDirectActivity) : Thread() {

    protected var port : Int = 4242

    protected lateinit var inputStream : InputStream
    protected lateinit var outputStream: OutputStream

    abstract fun initializeListen()

    fun write(byteArray: ByteArray){
        outputStream.write(byteArray)
    }

    override fun run(){

        initializeListen()

        val executor : ExecutorService = Executors.newSingleThreadExecutor()
        val handle = Handler(Looper.getMainLooper())

        executor.execute {

            val buffer = ByteArray(1024)
            var bytes : Int

            while(true){
                bytes = inputStream.read(buffer)
                if(bytes > 0){
                    val finalBytes : Int = bytes
                    handle.post {
                        val tempMsg = String(buffer, 0, finalBytes)
                        //wifiActivity.messages_received.add(tempMsg)
                        Toast.makeText(wifiActivity, tempMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

}