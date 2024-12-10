package com.mobile.giku.repository.nutrient

import com.mobile.giku.model.remote.nutrient.NutrientApiService
import com.mobile.giku.model.remote.nutrient.NutritionPredictionResponse
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NutrientRepository(private val apiService: NutrientApiService) {

    suspend fun getNutritionPrediction(image: MultipartBody.Part): NutritionPredictionResponse {
        try {
            val response: Response<NutritionPredictionResponse> = apiService.nutritionPrediction(image)
            if (response.isSuccessful) {
                return response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw IOException("Network error occurred: ${e.message}", e)
        } catch (e: HttpException) {
            throw HttpException(Response.error<NutritionPredictionResponse>(e.code(), e.response()?.errorBody()))
        } catch (e: Exception) {
            throw Exception("Unexpected error occurred: ${e.message}", e)
        }
    }
}