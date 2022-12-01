package com.example.tappze.di

import android.content.Context
import androidx.room.Room
import com.example.tappze.data.model.UserDao
import com.example.tappze.data.model.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserDaoModule {

    @Provides
    @Singleton
    fun providesUserDatabaseInstance(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesUserDaoInstance(database: UserDatabase): UserDao = database.userDao()
}