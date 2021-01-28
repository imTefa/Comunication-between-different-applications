package com.example.receiver.ui.main.listfragment

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.receiver.models.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 *
 *Created by Atef on 28/01/21
 *
 */
class UserListViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val usersList = userRepository.userList

}