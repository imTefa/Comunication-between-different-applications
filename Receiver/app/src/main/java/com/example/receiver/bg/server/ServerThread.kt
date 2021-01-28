package com.example.receiver.bg.server

import android.text.TextUtils
import android.util.Log
import com.example.receiver.bg.events.MessageEvent
import org.greenrobot.eventbus.EventBus
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

/**
 *
 *Created by Atef on 27/01/21
 *
 */
class ServerThread : Thread() {

    private val TAG = ServerThread::class.simpleName
    private val SERVER_PORT = 8080

    lateinit var serverSocket: ServerSocket
    lateinit var socket: Socket
    lateinit var writer: PrintWriter
    lateinit var reader: BufferedReader

    override fun run() {
        super.run()

        try {
            serverSocket = ServerSocket(8080)
            serverSocket.reuseAddress = true
            reconnect()

            Log.i(TAG, "Socket connected")

            while (true) {
                val message: String? = reader.readLine()
                if (!TextUtils.isEmpty(message)) {
                    Log.i(TAG, message!!)
                    EventBus.getDefault().post(MessageEvent(message))
                } else {
                    reconnect()
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
            socket.close()
            Log.e(TAG, "Socket error")
        }
    }

    private fun reconnect() {
        Log.i(TAG, "reconnect: ")
        socket = serverSocket.accept()
        writer = PrintWriter(socket.getOutputStream())
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
    }

    fun reply(response: String) {
        Thread(WritingRunnable(response)).start()
    }

    override fun interrupt() {
        serverSocket.close()
        super.interrupt()
    }


    inner class WritingRunnable(private val message: String) : Runnable {
        override fun run() {
            writer.write(message + "\n")
            writer.flush()
        }
    }
}