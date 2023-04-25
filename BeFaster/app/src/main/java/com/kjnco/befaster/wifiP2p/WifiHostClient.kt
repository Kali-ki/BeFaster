package com.kjnco.befaster.wifiP2p

import android.os.Handler
import android.os.Looper
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class WifiHostClient(private var wifiActivity : WifiDirectActivity) : Thread() {

    protected var port : Int = 4242

    protected lateinit var inputStream : InputStream
    protected lateinit var outputStream: OutputStream

    private var executor : ExecutorService = Executors.newSingleThreadExecutor()

    protected abstract fun initializeListen()

    abstract fun endListen()

    override fun interrupt(){
        super.interrupt()
        executor.shutdownNow()
        endListen()
    }

    fun write(byteArray: ByteArray){
        outputStream.write(byteArray)
    }

    override fun run(){

        initializeListen()

        Looper.prepare()
        val handle = Looper.myLooper()?.let { Handler(it) }

        executor.execute {

            val buffer = ByteArray(1024)
            var bytes : Int

            while(!currentThread().isInterrupted){

                if(inputStream.available() == 0){
                    continue
                }

                bytes = inputStream.read(buffer)

                if(bytes > 0){
                    val finalBytes : Int = bytes
                    handle?.post {
                        val tempMsg = String(buffer, 0, finalBytes)
                        wifiActivity.answers.add(tempMsg)
                        wifiActivity.semaphore.release()
                    }
                }

            }

        }

        Looper.loop()

    }

}