package com.mobile.giku.model.remote.articles

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val articles: List<Article>
)

data class Article(
    @SerializedName("id")
    val id: String,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Author")
    val author: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("photoURL")
    val photoUrl: String?,
    @SerializedName("Date")
    val date: String?
)