package com.mobile.giku.repository.nutrient

import android.util.Log
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.nutrient.NutrientApiService
import com.mobile.giku.model.remote.nutrient.NutritionPredictionResponse
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NutrientRepository(private val apiService: NutrientApiService, private val authDataStore: AuthDataStore) {

    suspend fun getNutritionPrediction(image: MultipartBody.Part): NutritionPredictionResponse {
        try {
            val token = authDataStore.getToken().first() ?: throw IllegalStateException("Token is missing")

            val response: Response<NutritionPredictionResponse> = apiService.nutritionPrediction("Bearer $token", image)
            if (response.isSuccessful) {
                return response.body() ?: throw IllegalStateException("Response body is null")
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            Log.e("NutrientRepository", "Network error occurred: ${e.message}", e)
            throw IOException("Network error occurred: ${e.message}", e)
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Server error"
            Log.e("NutrientRepository", "HTTP Error: $errorMessage", e)
            throw Exception("HTTP Error: $errorMessage", e)
        } catch (e: Exception) {
            Log.e("NutrientRepository", "Unexpected error occurred: ${e.message}", e)
            throw Exception("Unexpected error occurred: ${e.message}", e)
        }
    }
}