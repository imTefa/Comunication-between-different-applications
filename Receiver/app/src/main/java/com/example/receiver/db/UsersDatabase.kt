package com.example.receiver.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
 *Created by Atef on 28/01/21
 *
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}