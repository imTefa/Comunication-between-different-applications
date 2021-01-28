package com.example.receiver.bg.reveivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.receiver.ui.main.MainActivity

/**
 *
 *Created by Atef on 28/01/21
 *
 */
class NewUserReceiver : BroadcastReceiver() {

    private val TAG = NewUserReceiver::class.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val userJson = intent.getStringExtra("data")

        Intent(context, MainActivity::class.java).apply {
            putExtra("data", userJson)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }
}