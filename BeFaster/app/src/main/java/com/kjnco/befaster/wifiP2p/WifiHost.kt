package com.kjnco.befaster.wifiP2p

import java.net.ServerSocket
import java.net.Socket

class WifiHost(
    wifiActivity : WifiDirectActivity
    ) : WifiHostClient(wifiActivity) {

    private lateinit var serverSocket: ServerSocket

    override fun initializeListen() {
        serverSocket = ServerSocket(port)
        val socket : Socket = serverSocket.accept()
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()
    }

    override fun endListen() {
        serverSocket.close()
    }

}