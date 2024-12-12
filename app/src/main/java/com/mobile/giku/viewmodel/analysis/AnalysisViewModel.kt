package com.mobile.giku.viewmodel.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.remote.nutrient.NutritionPredictionResponse
import com.mobile.giku.repository.nutrient.NutrientRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AnalysisViewModel(private val repository: NutrientRepository) : ViewModel() {

    private val _analysisState = MutableLiveData<UIState>()
    val analysisState: LiveData<UIState> = _analysisState

    private val _nutritionResponse = MutableLiveData<NutritionPredictionResponse>()
    val nutritionResponse: LiveData<NutritionPredictionResponse> = _nutritionResponse

    fun analyze(image: File) {
        if (!image.exists()) {
            _analysisState.value = UIState.Error("Please select an image first")
            return
        }

        _analysisState.value = UIState.Loading
        viewModelScope.launch {
            try {
                val requestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", image.name, requestBody)

                val response = repository.getNutritionPrediction(imagePart)
                _nutritionResponse.value = response
                _analysisState.value = UIState.Success

            } catch (e: Exception) {
                _analysisState.value = UIState.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    fun resetState() {
        _analysisState.value = UIState.Idle
    }

    fun clearNutritionResponse() {
        _nutritionResponse.value = null
    }
}