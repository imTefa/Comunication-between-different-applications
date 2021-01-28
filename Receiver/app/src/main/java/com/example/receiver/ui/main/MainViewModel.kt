package com.example.receiver.ui.main

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receiver.models.User
import com.example.receiver.models.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

/**
 *
 *Created by Atef on 28/01/21
 *
 */
class MainViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
}