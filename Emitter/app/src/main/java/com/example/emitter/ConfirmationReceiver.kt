package com.example.emitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.emitter.models.getUserObject
import com.example.emitter.ui.MainActivity

/**
 *
 *Created by Atef on 27/01/21
 *
 */
class ConfirmationReceiver : BroadcastReceiver() {

    private val TAG = ConfirmationReceiver::class.simpleName

    init {
        Log.i(TAG, "receiver initiated")
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "onReceive: ")

        val userJson = intent.getStringExtra("data")
        val user = userJson?.let { getUserObject(it) }
        val received = intent.getBooleanExtra("received", false)


        Intent(context, MainActivity::class.java).apply {
            if (received)
                putExtra("message", context.getString(R.string.message_received_user_success,user?.name))
            else putExtra("message", context.getString(R.string.message_received_user_failure,user?.name))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }

}