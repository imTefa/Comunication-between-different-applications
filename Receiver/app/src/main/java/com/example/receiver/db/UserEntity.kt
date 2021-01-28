package com.example.receiver.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *Created by Atef on 28/01/21
 *
 */
@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey
    var id: Long,
    var name: String,
    @ColumnInfo(name = "username")
    var userName: String,
    var email: String,
    var phone: String,
    @ColumnInfo(name = "website")
    var webSite: String
)