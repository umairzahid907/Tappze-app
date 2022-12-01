package com.example.tappze.data.repository

import android.net.Uri
import com.example.tappze.data.model.User
import com.example.tappze.data.model.UserDao
import com.example.tappze.util.FireStoreTables
import com.example.tappze.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImp(
    private val userDao: UserDao,
    private val database: FirebaseFirestore,
    private val storageReference: StorageReference
): UserRepository {

    var user: User? = null
    override fun getUser(result: (UiState<User?>) -> Unit) {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                user = userDao.getUserFromDatabase()
            }.invokeOnCompletion {
                if (user?.id?.isNotEmpty() == true) {
                    result.invoke(
                        UiState.Success(user)
                    )
                }else{
                    result.invoke(
                        UiState.Failure("")
                    )
                }
            }
        }catch (e: Exception){
            result.invoke(
                UiState.Failure(e.localizedMessage)
            )
        }
    }

    override fun updateUser(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreTables.USER).document(user.id)
        document
            .set(user)
            .addOnSuccessListener {
                CoroutineScope(Dispatchers.IO).launch {
                    userDao.updateUserRecord(user)
                }.invokeOnCompletion {
                    result.invoke(
                        UiState.Success("Profile updated")
                    )
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun deleteUser(result: (UiState<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteUserFromDatabase()
        }.invokeOnCompletion {
            result.invoke(
                UiState.Success("Sign out successfully")
            )
        }
    }

    override suspend fun uploadImage(fileUri: Uri, result: (UiState<Uri>) -> Unit) {
        try{
            val uri: Uri = withContext(Dispatchers.IO){
                storageReference
                    .putFile(fileUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
            result.invoke(UiState.Success(uri))
        }catch(e: FirebaseFirestoreException){
            result.invoke(UiState.Failure(e.localizedMessage))
        }catch(e: Exception){
            result.invoke(UiState.Failure(e.localizedMessage))
        }
    }
}