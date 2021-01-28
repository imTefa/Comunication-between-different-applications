package com.example.receiver.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.receiver.R
import com.example.receiver.bg.events.ResponseEvent
import com.example.receiver.models.User
import com.example.receiver.models.getUserObject
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleActivityIntent(intent)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleActivityIntent(intent)
    }


    private fun handleActivityIntent(intent: Intent?) {
        if (intent != null) {
            val userJson = intent.getStringExtra("data")
            if (!userJson.isNullOrEmpty())
                showNewUserDialog(getUserObject(userJson))
        }
    }

    private fun showNewUserDialog(user: User?) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.message_new_user, user?.name))
            .setPositiveButton(getString(R.string.btn_accept)) { dialog, _ ->
                viewModel.insertUser(user!!)
                EventBus.getDefault().post(ResponseEvent("OK"))
                dialog.dismiss()
            }.setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ ->
                EventBus.getDefault().post(ResponseEvent("NOK"))
                dialog.dismiss()
            }.show()
    }
}