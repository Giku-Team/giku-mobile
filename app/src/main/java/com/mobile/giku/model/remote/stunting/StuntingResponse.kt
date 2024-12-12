package com.mobile.giku.model.remote.stunting

import com.google.gson.annotations.SerializedName

data class StuntingResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("weight")
    val weight: Double,

    @SerializedName("height")
    val height: Double,

    @SerializedName("bloodType")
    val bloodType: String,

    @SerializedName("fatherHeight")
    val fatherHeight: Double,

    @SerializedName("motherHeight")
    val motherHeight: Double,

    @SerializedName("photoURL")
    val photoURL: String?,

    @SerializedName("allergies")
    val allergies: List<Allergy>
)