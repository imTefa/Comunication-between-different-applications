package com.example.middleman.bg

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.middleman.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

/**
 *
 *Created by Atef on 28/01/21
 *
 */
class SocketWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val TAG = SocketWorker::class.simpleName

    override fun doWork(): Result {
        Log.i(TAG, "doWork")
        val userJson = inputData.getString("user")

        try {

            val socket = Socket("localhost", 8080)
            val output = PrintWriter(socket.getOutputStream())
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))

            output.write(userJson + "\n")
            output.flush()

            val response = input.readLine()
            if (response.isNotEmpty()) {
                Log.i(TAG, "doWork: $response")
                sendConfirmationToEmitter(
                    applicationContext,
                    userJson!!,
                    response == "OK"
                )
            }

            socket.close()

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
        return Result.success()
    }


    private fun sendConfirmationToEmitter(context: Context, user: String, status: Boolean) {
        Intent(context.getString(R.string.action_received_user)).also { intent ->
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.putExtra("data", user)
            intent.putExtra("received", status)
            intent.component =
                ComponentName(
                    context.getString(R.string.emitter_app_pkg),
                    context.getString(R.string.emitter_app_receiver_name)
                )
            context.sendBroadcast(intent)
        }
    }

}