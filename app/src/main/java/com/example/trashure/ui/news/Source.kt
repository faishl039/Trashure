package com.example.trashure.ui.news

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: Any?,
    @SerializedName("name")
    val name: String
)