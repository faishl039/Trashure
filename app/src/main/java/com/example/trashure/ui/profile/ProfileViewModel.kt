package com.example.trashure.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.pref.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoggingOut = MutableLiveData<Boolean>()
    val isLoggingOut: LiveData<Boolean> get() = _isLoggingOut

    private val _logoutComplete = MutableLiveData<Boolean>()
    val logoutComplete: LiveData<Boolean> get() = _logoutComplete

    fun logout() {
        _isLoggingOut.value = true
        viewModelScope.launch {
            repository.logout()
            _isLoggingOut.value = false
            _logoutComplete.value = true
        }
    }

    fun getSession() = repository.getSession().asLiveData()
}
