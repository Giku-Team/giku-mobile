package com.mobile.giku.model.remote.child

import com.google.gson.annotations.SerializedName

data class GetChildrenResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<ChildData>
)

data class ChildData(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("height") val height: String,
    @SerializedName("bloodType") val bloodType: String,
    @SerializedName("fatherHeight") val fatherHeight: String,
    @SerializedName("motherHeight") val motherHeight: String,
    @SerializedName("photoURL") val photoURL: String,
    @SerializedName("allergies") val allergies: List<Allergy>
)