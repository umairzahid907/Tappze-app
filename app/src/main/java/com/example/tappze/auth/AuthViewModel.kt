package com.example.tappze.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tappze.data.model.User
import com.example.tappze.data.repository.AuthRepository
import com.example.tappze.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    private val _forgot = MutableLiveData<UiState<String>>()
    val forgot: LiveData<UiState<String>>
        get() = _forgot

    fun register(
        email: String,
        password: String,
        user: User
    ){
        _register.value = UiState.Loading
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ){
            _register.value = it
        }
    }

    fun login(email: String, password: String){
        _login.value = UiState.Loading
        repository.loginUser(
            email = email,
            password = password
        ){
            _login.value = it
        }
    }

    fun forgotPassword(email: String){
        _forgot.value = UiState.Loading
        repository.forgotPassword(email = email){
            _forgot.value = it
        }
    }

}