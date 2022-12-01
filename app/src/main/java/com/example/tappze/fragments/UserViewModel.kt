package com.example.tappze.fragments

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tappze.data.model.User
import com.example.tappze.data.repository.UserRepository
import com.example.tappze.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _user = MutableLiveData<UiState<User?>>()
    val user: LiveData<UiState<User?>>
        get() = _user

    private val _updateUser = MutableLiveData<UiState<String>>()
    val updateUser: LiveData<UiState<String>>
        get() = _updateUser

    private val _deleteUser = MutableLiveData<UiState<String>>()
    val deleteUser: LiveData<UiState<String>>
        get() = _deleteUser

    fun user(){
        _user.value = UiState.Loading
        repository.getUser{
            _user.postValue(it)
        }
    }

    fun updateUser(user: User){
        _updateUser.value = UiState.Loading
        repository.updateUser(user){
            _updateUser.postValue(it)
        }
    }

    fun deleteUser(){
        _deleteUser.value = UiState.Loading
        repository.deleteUser(){
            _deleteUser.postValue(it)
        }
    }

    fun uploadImage(fileUri: Uri, result: (UiState<Uri>) -> Unit){
        result.invoke(UiState.Loading)
        viewModelScope.launch {
            repository.uploadImage(fileUri, result)
        }
    }
}