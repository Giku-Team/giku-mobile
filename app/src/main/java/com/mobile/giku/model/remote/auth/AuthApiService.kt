package com.mobile.giku.model.remote.auth

import com.mobile.giku.BuildConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(BuildConfig.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST(BuildConfig.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<Unit>
}