package com.example.tappze.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    suspend fun getUserFromDatabase(): User

    @Update(entity = User::class)
    suspend fun updateUserRecord(user: User)

    @Insert
    suspend fun addUserToDatabase(user: User)

    @Query("DELETE from user")
    suspend fun deleteUserFromDatabase()
}