package com.mobile.giku.model.remote.auth

data class ValidateResetCodeRequest(
    val email: String,
    val verificationCode: String
)
