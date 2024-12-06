package com.mobile.giku.utils

import com.mobile.giku.R

sealed class AuthErrorMapper {

    abstract fun mapError(errorCode: Int): String

    data class LoginErrorMapper(private val stringProvider: StringProvider) : AuthErrorMapper() {
        override fun mapError(errorCode: Int): String {
            return when (errorCode) {
                200 -> stringProvider.getString(R.string.successful_login)
                400 -> stringProvider.getString(R.string.invalid_credentials)
                408 -> stringProvider.getString(R.string.error_request_timeout)
                500 -> stringProvider.getString(R.string.error_internal_server_error)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }
    }

    data class RegisterErrorMapper(private val stringProvider: StringProvider) : AuthErrorMapper() {
        override fun mapError(errorCode: Int): String {
            return when (errorCode) {
                201 -> stringProvider.getString(R.string.user_registered_successfully)
                400 -> stringProvider.getString(R.string.error_user_already_exists)
                408 -> stringProvider.getString(R.string.error_request_timeout)
                500 -> stringProvider.getString(R.string.error_internal_server_error)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }
    }

    data class ForgotPasswordErrorMapper(private val stringProvider: StringProvider) : AuthErrorMapper() {
        override fun mapError(errorCode: Int): String {
            return when (errorCode) {
                200 -> stringProvider.getString(R.string.verification_code_sent_to_email)
                404 -> stringProvider.getString(R.string.error_user_not_found)
                408 -> stringProvider.getString(R.string.error_request_timeout)
                500 -> stringProvider.getString(R.string.error_internal_server_error)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }
    }

    data class VerificationCodeErrorMapper(private val stringProvider: StringProvider) : AuthErrorMapper() {
        override fun mapError(errorCode: Int): String {
            return when (errorCode) {
                200 -> stringProvider.getString(R.string.verification_successful)
                400 -> stringProvider.getString(R.string.invalid_verification_code)
                404 -> stringProvider.getString(R.string.error_user_not_found)
                408 -> stringProvider.getString(R.string.error_request_timeout)
                410 -> stringProvider.getString(R.string.verification_code_expired)
                500 -> stringProvider.getString(R.string.error_internal_server_error)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }
    }

    data class SetNewPasswordErrorMapper(private val stringProvider: StringProvider) : AuthErrorMapper() {
        override fun mapError(errorCode: Int): String {
            return when (errorCode) {
                    200 -> stringProvider.getString(R.string.password_updated_successfully)
                403 -> stringProvider.getString(R.string.verification_required)
                404 -> stringProvider.getString(R.string.error_user_not_found)
                408 -> stringProvider.getString(R.string.error_request_timeout)
                410 -> stringProvider.getString(R.string.reset_time_expired)
                500 -> stringProvider.getString(R.string.error_internal_server_error)
                else -> stringProvider.getString(R.string.error_unknown)
            }
        }
    }
}
