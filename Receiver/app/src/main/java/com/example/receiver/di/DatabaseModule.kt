package com.example.receiver.di

import android.content.Context
import androidx.room.Room
import com.example.receiver.db.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 *
 *Created by Atef on 28/01/21
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseObject(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(context, UsersDatabase::class.java, "users_database")
            // Wipes and rebuilds instead of migrating if no Migration object.
            .fallbackToDestructiveMigration()
            .build()
    }

}