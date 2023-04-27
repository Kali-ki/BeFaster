package com.kjnco.befaster.wifiP2p

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

/**
 * This class is used to create a client socket to connect to a host socket
 * extending the WifiHostClient class
 * @param hostAddress the address of the host socket
 * @param wifiCommunication the WifiCommunication object used to communicate with the host socket
 * @see WifiHostClient
 */
class WifiClient private constructor(
    hostAddress : InetAddress,
    wifiCommunication : WifiCommunication
    ) : WifiHostClient(wifiCommunication) {

    // The address of the host socket
    private var hostAdd : String = hostAddress.hostAddress as String
    // The socket used to communicate with the host socket
    private var socket : Socket = Socket()

    /**
     * This function is used to initialize the socket and connect to the host socket
     */
    override fun initializeListen() {
        socket.connect(InetSocketAddress(hostAdd, port), 500)
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()
    }

    /**
     * This function is used to close the socket
     */
    override fun endListen() {
        socket.close() // Close automatically closes the input and output streams as well
    }

    // Companion object to implement the Singleton pattern
    companion object {
        //Singleton
        private var instance : WifiClient? = null

        var isRunning : Boolean = false

        fun getInstance(hostAddress: InetAddress, wifiCommunication: WifiCommunication) : WifiClient {
            if (instance == null) {
                instance = WifiClient(hostAddress, wifiCommunication)
            }
            return instance!!
        }
    }

}