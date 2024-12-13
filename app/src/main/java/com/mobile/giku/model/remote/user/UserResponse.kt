package com.mobile.giku.model.remote.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("data")
    val data: UserData?
)

data class UserData(
    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("dob")
    val dob: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("createdAt")
    val createdAt: String?
)