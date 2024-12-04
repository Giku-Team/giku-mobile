package com.mobile.giku.model.remote.auth

data class ResetPasswordRequest(
    val email: String,
    val newPassword: String
)