package com.example.trashure.data.injection

import android.content.Context
import com.example.trashure.data.api.ApiConfig
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.pref.UserRepository
import com.example.trashure.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig().getApiService(user.accessToken)
        return UserRepository.getInstance(apiService, pref)
    }
}
