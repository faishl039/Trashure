package com.example.trashure.ui.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trashure.network.ApiConfig
import com.example.trashure.network.response.TrashureUploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadVM : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _uploadTrash = MutableLiveData<TrashureUploadResponse>()
    val uploadTrash: LiveData<TrashureUploadResponse> = _uploadTrash

    fun postImageTrash(image: File) {
        _isLoading.value = true
        val imgRequestBody = RequestBody.create("image/jpeg".toMediaType(), image)
        val imgPart = MultipartBody.Part.createFormData("image", image.name, imgRequestBody)

        ApiConfig.getApi().postDetectTrash(imgPart)
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
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    companion object {
        const val TAG = "UPLOAD"
    }
}