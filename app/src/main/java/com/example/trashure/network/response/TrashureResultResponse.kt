package com.example.trashure.network.response

import com.google.gson.annotations.SerializedName

data class TrashureResultResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("Glass")
	val glass: Any? = null,

	@field:SerializedName("Cardboard")
	val cardboard: Any? = null,

	@field:SerializedName("Paper")
	val paper: Any? = null,

	@field:SerializedName("Plastic")
	val plastic: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("Metal")
	val metal: Any? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
