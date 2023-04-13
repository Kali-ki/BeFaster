package com.kjnco.befaster.wifiP2p

import android.os.Looper
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WifiClient(
    hostAddress : InetAddress,
    private var wifiActivity : WifiDirectActivity
    ) : Thread() {

    private var hostAdd : String
    private lateinit var inputStream : InputStream
    private lateinit var outputStream: OutputStream
    private var socket : Socket = Socket()

    init {
        hostAdd = hostAddress.hostAddress as String
    }

    fun write(byteArray: ByteArray){
        outputStream.write(byteArray)
    }

    override fun run() {
        socket.connect(InetSocketAddress(hostAdd, 4242), 500)
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        val handler : android.os.Handler = android.os.Handler(Looper.getMainLooper())

        executor.execute {

            val buffer = ByteArray(1024)
            var bytes : Int

            while(true){
                bytes = inputStream.read(buffer)
                if(bytes > 0){
                    val finalBytes : Int = bytes
                    handler.post {
                        val tempMsg = String(buffer, 0, finalBytes)
                        //wifiActivity.messages_received.add(tempMsg)
                        Toast.makeText(wifiActivity, tempMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

}