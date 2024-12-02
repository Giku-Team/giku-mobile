package com.mobile.giku.model.remote.auth

data class LoginResponse(
    val code: Int,
    val message: String,
    val token: String
)