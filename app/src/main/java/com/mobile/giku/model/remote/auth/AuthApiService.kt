package com.mobile.giku.model.remote.auth

import com.mobile.giku.BuildConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST(BuildConfig.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST(BuildConfig.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST(BuildConfig.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<GenericResponse>

    @POST(BuildConfig.VALIDATE_RESET_CODE_URL)
    suspend fun validateResetCode(@Body request: ValidateResetCodeRequest): Response<GenericResponse>

    @POST(BuildConfig.RESET_PASSWORD_URL)
    suspend fun  resetPassword(@Body request: ResetPasswordRequest): Response<GenericResponse>
}