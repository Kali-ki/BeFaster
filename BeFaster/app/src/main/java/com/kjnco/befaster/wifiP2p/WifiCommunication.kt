package com.kjnco.befaster.wifiP2p

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.LinkedList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

/**
 * Class that manage the communication between the two devices
 * Use it to send and receive messages
 * Use a semaphore to wait for a message
 * Use a linked list to store the messages
 */
class WifiCommunication private constructor() {

    // ServerClient thread
    internal lateinit var wifiServerClient : WifiHostClient

    // Semaphore and linked list to store the messages
    internal var semaphore : Semaphore = Semaphore(0)
    internal var answers : LinkedList<String> = LinkedList()

    /**
     * Send a message to the other device
     * @param msg : the message to send
     */
    fun sendMsg(msg : String){
        val executor : ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            wifiServerClient.write(msg.toByteArray())
        }
    }

    /**
     * Wait for a message and return it
     * @return the message received or null if timeout
     */
    suspend fun waitForMessageSuspend(): String? {
        return withTimeoutOrNull(5000){
            withContext(Dispatchers.IO) {
                semaphore.acquire()
            }
            return@withTimeoutOrNull answers.poll()
        }
    }

    suspend fun waitForMessageSuspendWithoutTimeout(): String? {
        return withContext(Dispatchers.IO) {
            semaphore.acquire()
            return@withContext answers.poll()
        }
    }

    /**
     * Stop the communication thread
     */
    fun stopThread(){
        wifiServerClient.interrupt()
        wifiServerClient.join()
    }

    // Companion object to implement a singleton
    companion object {

        // Singleton
        private var instance : WifiCommunication? = null

        fun getInstance() : WifiCommunication {
            if (instance == null) {
                instance = WifiCommunication()
            }
            return instance!!
        }

    }

}