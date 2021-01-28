package com.example.receiver.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *
 *Created by Atef on 28/01/21
 *
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(night: UserEntity)

    @Query("SELECT * FROM users_table")
    fun getAllNights(): LiveData<List<UserEntity>>
}