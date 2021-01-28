package com.example.middleman

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.*
import com.example.middleman.bg.SocketWorker
import com.example.middleman.models.getUserObject
import java.util.concurrent.TimeUnit

/**
 *
 *Created by Atef on 27/01/21
 *
 */
class UserReceiver : BroadcastReceiver() {

    private val TAG = UserReceiver::class.simpleName

    init {
        Log.i(TAG, "receiver initiated")
    }

    override fun onReceive(context: Context, intent: Intent) {
        val userJson = intent.getStringExtra("data")
        val user = userJson?.let { getUserObject(it) }
        Log.i(TAG, "onReceive: $user")


        val socketWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SocketWorker>()
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                ).addTag(System.currentTimeMillis().toString())
                .setInputData(workDataOf("user" to userJson))
                .build()

        WorkManager
            .getInstance(context)
            .enqueue(socketWorkRequest)
    }


}