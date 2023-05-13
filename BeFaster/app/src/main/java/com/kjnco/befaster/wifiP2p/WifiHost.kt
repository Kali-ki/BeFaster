package com.kjnco.befaster.wifiP2p

import java.net.ServerSocket
import java.net.Socket

/**
 * This class is used to create a host socket to listen for a client socket
 * extending the WifiHostClient class
 * @param wifiCommunication the WifiCommunication object used to communicate with the client socket
 */
class WifiHost private constructor(
    wifiCommunication : WifiCommunication
    ) : WifiHostClient(wifiCommunication) {

    // The socket used to communicate with the client socket
    private lateinit var serverSocket: ServerSocket

    /**
     * This function is used to initialize the socket and listen for a client socket
     */
    override fun initializeListen() {
        serverSocket = ServerSocket(getPort())
        val socket : Socket = serverSocket.accept()
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()
    }

    /**
     * This function is used to close the socket
     */
    override fun endListen() {
        inputStream.close()
        outputStream.close()
        serverSocket.close()
    }

    companion object {
        //Singleton
        private var instance : WifiHost? = null

        var isRunning : Boolean = false

        /**
         * Do not use it, use getInstance instead
         */
        internal fun getNewInstance(wifiCommunication: WifiCommunication) : WifiHost {
            instance = WifiHost(wifiCommunication)
            return instance as WifiHost
        }

    }

}