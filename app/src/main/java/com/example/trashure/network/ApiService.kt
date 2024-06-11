package com.example.trashure.network

import com.example.trashure.network.response.TrashureLoginResponse
import com.example.trashure.network.response.TrashureRegisterResponse
import com.example.trashure.network.response.TrashureResultResponse
import com.example.trashure.network.response.TrashureUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("api/auth/register")
    fun postRegister(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<TrashureRegisterResponse>

    @FormUrlEncoded
    @POST("api/auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<TrashureLoginResponse>

    @Multipart
    @POST("api/image")
    fun postDetectTrash(
        @Part image: MultipartBody.Part
    ): Call<TrashureUploadResponse>

    @GET("api/image")
    fun getResultTrash(): Call<TrashureResultResponse>
}