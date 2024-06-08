package com.example.trashure.ui.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Article(
    val name: String,
    val description1: String,
    val description2: String,
    val description3: String,
    val photo: Int,
) : Parcelable

