package com.example.emitter.network

import com.example.emitter.models.User
import retrofit2.http.GET

/**
 *
 *Created by Atef on 26/01/21
 *
 */
interface WebService {

    @GET("users")
    suspend fun getUsers(): List<User>
}