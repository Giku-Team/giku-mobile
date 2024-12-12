package com.mobile.giku.model.remote.stunting

import com.google.gson.annotations.SerializedName

data class StuntingRequest(
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

    @SerializedName("allergies")
    val allergies: List<Allergy>,

    @SerializedName("photo")
    val photo: String? = null // Optional photo
)

data class Allergy(
    @SerializedName("name")
    val name: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("severity")
    val severity: String
)