package com.mobile.giku.model.remote.auth

data class LoginRequest(
    val email: String,
    val password: String,
)
