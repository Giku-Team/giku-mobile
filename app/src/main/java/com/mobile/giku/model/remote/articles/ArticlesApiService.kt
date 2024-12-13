package com.mobile.giku.model.remote.articles

import com.mobile.giku.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesApiService {
    @GET(BuildConfig.ARTICLES_URL)
    suspend fun getArticles(): Response<ArticleResponse>
}