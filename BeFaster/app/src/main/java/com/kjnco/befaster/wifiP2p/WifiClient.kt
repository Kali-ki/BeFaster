package com.kjnco.befaster.wifiP2p

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

class WifiClient(
    hostAddress : InetAddress,
    wifiActivity : WifiDirectActivity
    ) : WifiHostClient(wifiActivity) {

    private var hostAdd : String = hostAddress.hostAddress as String
    private var socket : Socket = Socket()

    override fun initializeListen() {
        socket.connect(InetSocketAddress(hostAdd, port), 500)
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()
    }

    override fun endListen() {
        socket.close()
    }

}