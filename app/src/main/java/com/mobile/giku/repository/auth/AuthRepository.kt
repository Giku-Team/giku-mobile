package com.mobile.giku.repository.auth

import com.mobile.giku.model.remote.auth.AuthApiService
import com.mobile.giku.model.remote.auth.LoginRequest
import com.mobile.giku.model.remote.auth.LoginResponse
import com.mobile.giku.model.remote.auth.RegisterRequest
import com.mobile.giku.model.remote.auth.RegisterResponse
import retrofit2.Response

class AuthRepository(private val apiService: AuthApiService) {

    private fun <T> handleResponse(response: Response<T>): Result<T> {
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: run {
                Result.failure(Exception("Empty response body"))
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("Error: ${response.code()} - $errorBody"))
        }
    }

    suspend fun register(email: String, password: String, name: String): Result<RegisterResponse> {
        return try {
            val response = apiService.register(RegisterRequest(email, password, name))
            handleResponse(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            handleResponse(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
