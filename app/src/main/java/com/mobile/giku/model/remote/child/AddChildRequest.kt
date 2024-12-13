package com.mobile.giku.model.remote.child

import com.google.gson.annotations.SerializedName

data class AddChildRequest(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("weight") val weight: Double,
    @SerializedName("height") val height: Double,
    @SerializedName("bloodType") val bloodType: String,
    @SerializedName("fatherHeight") val fatherHeight: Double,
    @SerializedName("motherHeight") val motherHeight: Double,
    @SerializedName("allergies") val allergies: List<Allergy>?,
    @SerializedName("photo") val photo: String? = null
)