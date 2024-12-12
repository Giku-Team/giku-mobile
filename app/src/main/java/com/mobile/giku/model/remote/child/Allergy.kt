package com.mobile.giku.model.remote.child

import com.google.gson.annotations.SerializedName

data class Allergy(
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("severity") val severity: String
)