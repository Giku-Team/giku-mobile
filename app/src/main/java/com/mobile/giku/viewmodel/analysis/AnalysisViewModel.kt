package com.mobile.giku.viewmodel.analysis

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.remote.nutrient.KandunganGizi
import com.mobile.giku.model.remote.nutrient.NutritionPredictionResponse
import com.mobile.giku.repository.NutrientRepository
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

    val selectedImageUri = MutableLiveData<Uri>()

    fun analyze(image: File) {
        if (
            !image.exists()
            || !image.name.endsWith(".jpg", true)
            && !image.name.endsWith(".png", true)
            && !image.name.endsWith(".jpeg", true)
            ) {
            _analysisState.value = UIState.Error("Please select a valid image file")
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
        _nutritionResponse.value = NutritionPredictionResponse(
            code = 0,
            confidenceScore = "",
            kandunganGizi = KandunganGizi(
                biotin = 0.0, fenilalaninG = 0.0, fosfor = 0.0, gulaG = 0.0, iodium = 0.0,
                isoleusinG = 0.0, kalium = 0.0, kaloriKcal = 0.0, kalsium = 0.0, karbohidratG = 0.0,
                kloridaG = 0.0, kolin = 0.0, lemakG = 0.0, leusinG = 0.0, lisinG = 0.0, magnesium = 0.0,
                metioninG = 0.0, natriumG = 0.0, proteinG = 0.0, selenium = 0.0, taurinG = 0.0, tembaga = 0.0,
                threoninG = 0.0, triptofanG = 0.0, valinG = 0.0, vitaminA = 0.0, vitaminB12Kobalamin = 0.0,
                vitaminB1Tiamin = 0.0, vitaminB2Riboflavin = 0.0, vitaminB3Niasin = 0.0,
                vitaminB5AsamPantotenat = 0.0, vitaminB6Piridoksin = 0.0, vitaminB9AsamFolat = 0.0,
                vitaminC = 0.0, vitaminD = 0.0, vitaminE = 0.0, vitaminK = 0.0, zatBesi = 0.0, zink = 0.0
            ),
            message = "",
            namaMakanan = ""
        )
    }
}
