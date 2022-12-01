package com.example.tappze.data.repository

import com.example.tappze.data.model.User
import com.example.tappze.data.model.UserDao
import com.example.tappze.util.FireStoreTables
import com.example.tappze.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthRepositoryImp(
    val auth: FirebaseAuth,
    private val database: FirebaseFirestore,
    val userDao: UserDao
) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        user: User,
        result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document =
                        database.collection(FireStoreTables.USER).document(it.result.user?.uid!!)
                    user.id = document.id
                    document
                        .set(user)
                        .addOnSuccessListener {
                            CoroutineScope(Dispatchers.IO).launch {
                                userDao.addUserToDatabase(user)
                            }
                            result.invoke(
                                UiState.Success("User registered successfully")
                            )
                        }
                        .addOnFailureListener { error ->
                            result.invoke(
                                UiState.Failure(error.localizedMessage)
                            )
                        }

                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(
                            UiState.Failure("Authentication failed, Password must be at least 6 characters")
                        )
                    } catch (e: FirebaseAuthEmailException) {
                        result.invoke(
                            UiState.Failure("Authentication failed, Invalid Email entered")
                        )
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(
                            UiState.Failure("Authentication failed, Email already registered")
                        )
                    } catch (e: java.lang.Exception) {
                        result.invoke(
                            UiState.Failure(e.message)
                        )
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.uid?.let { it1 -> addUserToLocalDatabase(it1) }
                result.invoke(
                    UiState.Success("Logged in successfully")
                )
            }
            .addOnFailureListener { error ->
                result.invoke(
                    UiState.Failure(error.localizedMessage)
                )
            }
    }

    override fun forgotPassword(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(
                        UiState.Success("Email sent successfully")
                    )
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    private fun addUserToLocalDatabase(id: String){
       database.collection(FireStoreTables.USER).document(id)
           .get()
           .addOnSuccessListener {
               val user = it.toObject(User::class.java)
               CoroutineScope(Dispatchers.IO).launch {
                   user?.let { it1 -> userDao.addUserToDatabase(it1) }
               }
           }
    }

}