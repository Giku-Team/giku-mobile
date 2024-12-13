package com.mobile.giku.model.remote.user

import com.mobile.giku.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET(BuildConfig.USER_URL)
    suspend fun getUser(
        @Path("userId") userId: String
    ): Response<UserResponse>
}