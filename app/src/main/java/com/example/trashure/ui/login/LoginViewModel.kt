package com.example.trashure.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.LoginResponse
import com.example.trashure.data.pref.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    fun login(email: String, password: String) {
        _loginStatus.value = true
        userRepository.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _loginStatus.value = false
                if (response.isSuccessful) {
                    _loginResult.value = Result.success(response.body()!!)
                } else {
                    _loginResult.value = Result.failure(Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginStatus.value = false
                _loginResult.value = Result.failure(t)
            }
        })
    }

    fun saveSession(user: LoginResponse) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}
