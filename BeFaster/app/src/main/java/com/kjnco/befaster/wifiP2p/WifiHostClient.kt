package com.kjnco.befaster.wifiP2p

import android.os.Handler
import android.os.Looper
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Class to manage the communication between the host and the client
 * @param wifiCommunication the wifi communication class
 */
abstract class WifiHostClient (
    private var wifiCommunication: WifiCommunication
    ) : Thread() {

    // Port to use for the communication
    protected var port : Int = 4242

    // Input and output streams
    protected lateinit var inputStream : InputStream
    protected lateinit var outputStream: OutputStream

    /**
     * Method to initialize the input and output streams
     */
    protected abstract fun initializeListen()

    /**
     * Method to end the listening
     */
    abstract fun endListen()

    /**
     * override of the interrupt method
     */
    override fun interrupt(){
        super.interrupt()
        endListen()
    }

    /**
     * Method to write a byte array to the output stream
     * @param byteArray the byte array to write
     */
    fun write(byteArray: ByteArray){
        outputStream.write(byteArray)
    }

    /**
     * Method to listen to the input stream
     */
    override fun run(){

        val executor : ExecutorService = Executors.newSingleThreadExecutor()

        // Initialize the listening
        initializeListen()

        // Create a handle and a looper to manage the thread
        Looper.prepare()
        val handle = Looper.myLooper()?.let { Handler(it) }

        // Listen to the input stream
        executor.execute {

            val buffer = ByteArray(1024)
            var bytes : Int

            while(!currentThread().isInterrupted){

                // Allow to not block the thread
                if(inputStream.available() == 0){
                    continue
                }

                // Read the input stream
                bytes = inputStream.read(buffer)

                if(bytes > 0){
                    val finalBytes : Int = bytes
                    handle?.post {
                        // Add the message to the list of answers and release the semaphore
                        val tempMsg = String(buffer, 0, finalBytes)
                        wifiCommunication.answers.add(tempMsg)
                        wifiCommunication.semaphore.release()
                    }
                }

            }

        }

        // Start the looper
        Looper.loop()

    }

}