package com.example.tappze.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters( HashMapConverter::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}