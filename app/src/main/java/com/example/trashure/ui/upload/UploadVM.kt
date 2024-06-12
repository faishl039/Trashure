package com.example.trashure.ui.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trashure.data.pref.UserRepository
import com.example.trashure.network.response.TrashureResultResponse
import com.example.trashure.network.response.TrashureUploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadVM(private val repository: UserRepository) : ViewModel() {
    val upImageResp: LiveData<TrashureUploadResponse> = repository.uploadTrash
    val resultResp: LiveData<TrashureResultResponse> = repository.resultTrash
    val isLoad: LiveData<Boolean> = repository.isLoading

    fun postImageTrash(token: String, image: File) {
        repository.postImageTrash(token, image)
    }

    fun getResultTrash(token: String) {
        repository.getResultTrash(token)
    }

    fun getSession() = repository.getSession().asLiveData()
}