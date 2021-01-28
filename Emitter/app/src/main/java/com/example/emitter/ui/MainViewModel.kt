package com.example.emitter.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.StringRes
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.emitter.R
import com.example.emitter.models.User
import com.example.emitter.network.WebService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch


/**
 *
 *Created by Atef on 26/01/21
 *
 */

enum class UsersApiStatus { LOADING, ERROR, DONE }

class MainViewModel @ViewModelInject constructor(
    var webServicesApi: WebService,
    @ApplicationContext val context: Context,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val TAG = MainViewModel::class.simpleName

    private val _status = MutableLiveData<UsersApiStatus>()
    val status: LiveData<UsersApiStatus>
        get() = _status

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users


    init {
        getUsersList()
    }

    fun getUsersList() {
        viewModelScope.launch {
            _status.value = UsersApiStatus.LOADING
            try {
                _users.value = webServicesApi.getUsers()
                _status.value = UsersApiStatus.DONE
            } catch (e: Exception) {
                _status.value = UsersApiStatus.ERROR
                _users.value = ArrayList()
            }
        }
    }

    fun sendUserToMiddleApp(user: User) {
        Log.i(TAG, "sendUserToMiddleApp: $user")

        Intent(getString(R.string.action_send_user)).also { intent ->
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.putExtra("data", user.getUserJson())
            intent.component =
                ComponentName(
                    getString(R.string.middle_app_pkg),
                    getString(R.string.middle_app_receiver_name)
                )
            context.sendBroadcast(intent)
        }
    }


    private fun getString(@StringRes res: Int): String {
        return context.getString(res)
    }
}