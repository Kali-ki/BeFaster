package com.kjnco.befaster.wifiP2p

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WifiServer(private var wifiActivity : WifiDirectActivity) : Thread() {

    private lateinit var serverSocket: ServerSocket
    private lateinit var inputStream : InputStream
    private lateinit var outputStream: OutputStream

    override fun run() {

        serverSocket = ServerSocket(4242)
        val socket : Socket = serverSocket.accept()
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()

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

    fun write(byteArray: ByteArray){
        outputStream.write(byteArray)
    }

}