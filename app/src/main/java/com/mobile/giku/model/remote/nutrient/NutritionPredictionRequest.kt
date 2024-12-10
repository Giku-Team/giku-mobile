package com.mobile.giku.model.remote.nutrient

import okhttp3.MultipartBody

data class NutritionPredictionRequest(
    val image: MultipartBody.Part
)