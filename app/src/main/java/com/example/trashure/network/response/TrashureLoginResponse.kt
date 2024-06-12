package com.example.trashure.network.response

import com.google.gson.annotations.SerializedName

data class TrashureLoginResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
