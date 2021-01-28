package com.example.middleman.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize

/**
 *
 *Created by Atef on 26/01/21
 *
 */

fun getUserObject(userJson: String): User? {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
    return jsonAdapter.fromJson(userJson)
}

@Parcelize
data class User(
    var id: Long,
    var name: String,
    @Json(name = "username")
    var userName: String,
    var email: String,
    var phone: String,
    @Json(name = "website")
    var webSite: String
) : Parcelable {

    fun getUserJson(): String {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
        return jsonAdapter.toJson(this)
    }

}
