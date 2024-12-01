package com.mobile.giku.repository.auth

import com.mobile.giku.model.remote.auth.AuthApiService
import com.mobile.giku.model.remote.auth.LoginRequest
import com.mobile.giku.model.remote.auth.RegisterRequest
import retrofit2.Response

class AuthRepository(private val apiService: AuthApiService) {

    private fun handleResponse(response: Response<Unit>): Result<Unit> {
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Error: ${response.code()}"))
        }
    }

    suspend fun register(email: String, password: String, name: String): Result<Unit> {
        return try {
            val response: Response<Unit> = apiService.register(RegisterRequest(email, password, name))
            handleResponse(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val response: Response<Unit> = apiService.login(LoginRequest(email, password))
            handleResponse(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}