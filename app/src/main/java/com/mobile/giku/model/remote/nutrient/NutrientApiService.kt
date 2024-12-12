package com.mobile.giku.model.remote.nutrient

import com.mobile.giku.BuildConfig
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NutrientApiService {
    @Multipart
    @POST(BuildConfig.NUTRITION_PREDICTION_URL)
    suspend fun nutritionPrediction(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<NutritionPredictionResponse>
}