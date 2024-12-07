package com.mobile.giku.repository.auth

import com.mobile.giku.model.remote.auth.AuthApiService
import com.mobile.giku.model.remote.auth.ForgotPasswordRequest
import com.mobile.giku.model.remote.auth.GenericResponse
import com.mobile.giku.model.remote.auth.LoginRequest
import com.mobile.giku.model.remote.auth.LoginResponse
import com.mobile.giku.model.remote.auth.RegisterRequest
import com.mobile.giku.model.remote.auth.RegisterResponse
import com.mobile.giku.model.remote.auth.ResetPasswordRequest
import com.mobile.giku.model.remote.auth.ValidateResetCodeRequest
import com.mobile.giku.utils.AuthErrorMapper
import retrofit2.Response

class AuthRepository(
    private val apiService: AuthApiService,
    private val loginErrorMapper: AuthErrorMapper.LoginErrorMapper,
    private val registerErrorMapper: AuthErrorMapper.RegisterErrorMapper,
    private val forgotPasswordErrorMapper: AuthErrorMapper.ForgotPasswordErrorMapper,
    private val verificationCodeErrorMapper: AuthErrorMapper.VerificationCodeErrorMapper,
    private val setNewPasswordErrorMapper: AuthErrorMapper.SetNewPasswordErrorMapper
) {

    private fun <T> handleResponse(response: Response<T>, errorMapper: AuthErrorMapper): Result<T> {
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: run {
                Result.failure(Exception("Empty response body"))
            }
        } else {
            val errorCode = response.code()
            val errorMessage = errorMapper.mapError(errorCode)
            Result.failure(Exception(errorMessage))
        }
    }

    suspend fun register(email: String, password: String, name: String): Result<RegisterResponse> {
        return try {
            val response = apiService.register(RegisterRequest(email, password, name))
            handleResponse(response, registerErrorMapper)
        } catch (e: Exception) {
            Result.failure(Exception(registerErrorMapper.mapError(-1)))
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            handleResponse(response, loginErrorMapper)
        } catch (e: Exception) {
            Result.failure(Exception(loginErrorMapper.mapError(-1)))
        }
    }

    suspend fun forgotPassword(email: String): Result<GenericResponse> {
        return try {
            val response = apiService.forgotPassword(ForgotPasswordRequest(email))
            handleResponse(response, forgotPasswordErrorMapper)
        } catch (e: Exception) {
            Result.failure(Exception(forgotPasswordErrorMapper.mapError(-1)))
        }
    }

    suspend fun validateResetCode(email: String, verificationCode: String): Result<GenericResponse> {
        return try {
            val response = apiService.validateResetCode(ValidateResetCodeRequest(email, verificationCode))
            handleResponse(response, verificationCodeErrorMapper)
        } catch (e: Exception) {
            Result.failure(Exception(verificationCodeErrorMapper.mapError(-1)))
        }
    }

    suspend fun resetPassword(email: String, newPassword: String): Result<GenericResponse> {
        return try {
            val response = apiService.resetPassword(ResetPasswordRequest(email, newPassword))
            handleResponse(response, setNewPasswordErrorMapper)
        } catch (e: Exception) {
            Result.failure(Exception(setNewPasswordErrorMapper.mapError(-1)))
        }
    }
}