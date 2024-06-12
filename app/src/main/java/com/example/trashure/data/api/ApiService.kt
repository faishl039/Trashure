package com.example.trashure.data.api

import com.example.trashure.data.LoginResponse
import com.example.trashure.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)
