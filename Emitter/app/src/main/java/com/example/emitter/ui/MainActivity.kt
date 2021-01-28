package com.example.emitter.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.emitter.R
import com.example.emitter.databinding.ActivityMainBinding
import com.example.emitter.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.list.adapter = UsersAdapter(UsersAdapter.OnClickListener {
            showSendDialog(it)
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUsersList()
        }

        handleActivityIntent(intent)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleActivityIntent(intent)
    }

    private fun handleActivityIntent(intent: Intent?) {
        if (intent != null) {
            val message = intent.getStringExtra("message")
            if (!message.isNullOrEmpty())
                showReceivedDialog(message)
        }
    }

    private fun showReceivedDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showSendDialog(user: User) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.message_send_user, user.name))
            .setPositiveButton(getString(R.string.btn_send)) { dialog, _ ->
                viewModel.sendUserToMiddleApp(user)
                dialog.dismiss()
            }.setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}
