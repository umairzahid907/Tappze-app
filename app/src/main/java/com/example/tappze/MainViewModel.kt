package com.example.tappze

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tappze.data.model.User
import com.example.tappze.data.repository.UserRepository
import com.example.tappze.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
):  ViewModel() {
    private val _user = MutableLiveData<UiState<User?>>()
    val user: LiveData<UiState<User?>>
        get() = _user

    fun user(){
        _user.value = UiState.Loading
        repository.getUser{
            _user.postValue(it)
        }
    }
}