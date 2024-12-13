package com.mobile.giku.repository

import com.mobile.giku.model.remote.user.UserApiService
import com.mobile.giku.model.remote.user.UserResponse
import retrofit2.Response

class UserRepository(private val apiService: UserApiService) {

    suspend fun getUser(userId: String): Response<UserResponse> {
        return apiService.getUser(userId)
    }
}
