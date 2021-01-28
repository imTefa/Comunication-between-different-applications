package com.example.receiver.di

import com.example.receiver.db.UserMapper
import com.example.receiver.db.UsersDatabase
import com.example.receiver.models.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 *
 *Created by Atef on 28/01/21
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
class UserRepoModule {

    @Singleton
    @Provides
    fun provideUserRepo(database: UsersDatabase, userMapper: UserMapper): UserRepository {
        return UserRepository(database.userDao, userMapper)
    }
}