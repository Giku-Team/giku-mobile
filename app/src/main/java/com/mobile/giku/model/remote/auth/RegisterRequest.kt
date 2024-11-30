package com.mobile.giku.model.remote.auth

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)