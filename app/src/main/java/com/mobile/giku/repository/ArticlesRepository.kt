package com.mobile.giku.repository

import com.mobile.giku.model.remote.articles.ArticlesApiService
import com.mobile.giku.model.remote.articles.ArticleResponse
import retrofit2.Response
import java.io.IOException

class ArticlesRepository(private val apiService: ArticlesApiService) {

    suspend fun fetchArticles(): Result<ArticleResponse> {
        return try {
            val response: Response<ArticleResponse> = apiService.getArticles()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(IOException("Empty response body"))
            } else {
                Result.failure(IOException("Error occurred: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
