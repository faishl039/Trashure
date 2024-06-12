package com.example.trashure.data.pref

import com.example.trashure.data.LoginResponse
import com.example.trashure.data.RegisterResponse
import com.example.trashure.data.api.ApiService
import com.example.trashure.data.api.LoginRequest
import com.example.trashure.data.api.RegisterRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun login(email: String, password: String): Call<LoginResponse> {
        return apiService.login(LoginRequest(email, password))
    }

    suspend fun saveSession(user: LoginResponse) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<LoginResponse> {
        return userPreference.getSession()
    }

    fun register(name: String, email: String, password: String): Call<RegisterResponse> {
        return apiService.register(RegisterRequest(name, email, password))
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}