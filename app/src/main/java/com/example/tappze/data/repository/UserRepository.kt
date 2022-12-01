package com.example.tappze.data.repository

import android.net.Uri
import com.example.tappze.data.model.User
import com.example.tappze.util.UiState

interface UserRepository {
    fun getUser(result: (UiState<User?>) -> Unit)
    fun updateUser(user: User, result: (UiState<String>) -> Unit)
    fun deleteUser(result: (UiState<String>) -> Unit)
    suspend fun uploadImage(fileUri: Uri, result: (UiState<Uri>) -> Unit)
}