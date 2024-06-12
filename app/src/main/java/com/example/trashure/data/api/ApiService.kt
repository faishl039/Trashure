package com.example.trashure.data.api

import com.example.trashure.network.response.LoginResponse
import com.example.trashure.network.response.RegisterResponse
import com.example.trashure.network.response.TrashureResultResponse
import com.example.trashure.network.response.TrashureUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @Multipart
    @POST("image")
    fun postDetectTrash(
        @Part image: MultipartBody.Part
    ): Call<TrashureUploadResponse>

    @GET("image")
    fun getResultTrash(): Call<TrashureResultResponse>
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
