package com.example.receiver.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.receiver.db.UserDao
import com.example.receiver.db.UserMapper

/**
 *
 *Created by Atef on 28/01/21
 *
 */
class UserRepository(val userDao: UserDao, val mapper: UserMapper) {

    private val _usersList: LiveData<List<User>> =
        userDao.getAllNights().map { list -> mapper.mapFromEntityList(list) }

    val userList: LiveData<List<User>>
        get() = _usersList

    suspend fun insertUser(user: User) {
        userDao.insert(mapper.mapToEntity(user))
    }

}