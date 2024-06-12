package com.example.trashure.data.pref

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trashure.network.response.LoginResponse
import com.example.trashure.network.response.RegisterResponse
import com.example.trashure.data.api.ApiConfig
import com.example.trashure.data.api.ApiService
import com.example.trashure.data.api.LoginRequest
import com.example.trashure.data.api.RegisterRequest
import com.example.trashure.network.response.TrashureResultResponse
import com.example.trashure.network.response.TrashureUploadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _uploadTrash = MutableLiveData<TrashureUploadResponse>()
    val uploadTrash: LiveData<TrashureUploadResponse> = _uploadTrash

    private val _resultTrash = MutableLiveData<TrashureResultResponse>()
    val resultTrash: LiveData<TrashureResultResponse> = _resultTrash

    fun postImageTrash(token: String, image: File) {
        _isLoading.value = true
        val imgRequestBody = RequestBody.create("image/jpeg".toMediaType(), image)
        val imgPart = MultipartBody.Part.createFormData("image", image.name, imgRequestBody)

        ApiConfig.getApiService(token).postDetectTrash(imgPart)
            .enqueue(object : Callback<TrashureUploadResponse> {
                override fun onResponse(
                    call: Call<TrashureUploadResponse>,
                    response: Response<TrashureUploadResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _uploadTrash.value = response.body()
                    } else {
                        Log.e(
                            TAG, "onfailure: ${response.message()}, ${response.code()}, ${
                                response.errorBody()?.toString()
                            }"
                        )
                    }
                }

                override fun onFailure(call: Call<TrashureUploadResponse>, t: Throwable) {
                    _isLoading.value = false
//                    Toast.makeText(this, "onFailure: ${t.message}", Toast.LENGTH_SHORT).s
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun getResultTrash(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getResultTrash()
        client.enqueue(object : Callback<TrashureResultResponse> {
            override fun onResponse(
                call: Call<TrashureResultResponse>,
                response: Response<TrashureResultResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _resultTrash.value  = response.body()
                } else {
                    Log.e(
                        TAG, "onfailure: ${response.message()}, ${response.code()}, ${
                            response.errorBody()?.toString()
                        }"
                    )
                }
            }

            override fun onFailure(call: Call<TrashureResultResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

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
        private const val TAG = "USER_REPO"
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