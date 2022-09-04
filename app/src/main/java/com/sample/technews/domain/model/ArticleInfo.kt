package com.sample.technews.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleInfo(
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: String? = "",
    val title: String,
    val urlToImage: String? = ""
) : Parcelable