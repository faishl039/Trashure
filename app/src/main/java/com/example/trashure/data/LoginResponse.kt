package com.example.trashure.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(


	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("email")
	val email: String,

	val isLogin: Boolean = false

)
