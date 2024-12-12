package com.mobile.giku.view.ui.analysis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mobile.giku.databinding.FragmentAnalysisDetailsBinding
import com.mobile.giku.model.remote.nutrient.NutritionPredictionResponse
import com.mobile.giku.viewmodel.analysis.AnalysisViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnalysisDetailsFragment : Fragment() {

    private var _binding: FragmentAnalysisDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalysisViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.nutritionResponse.observe(viewLifecycleOwner) { response ->
            response?.apply {

                binding.tvProductName.text = this.namaMakanan

                binding.tvConfidenceScore.text = "Confidence: ${this.confidenceScore}"

                binding.tvNutritionalContent.text = formatNutritionDetails(this)

            }
        }
    }

    private fun formatNutritionDetails(response: NutritionPredictionResponse): String {
        val gizi = response.kandunganGizi

        return """
    Nutrisi Makanan: ${response.namaMakanan}

    Informasi Gizi:
    * Kalori: ${gizi.kaloriKcal} kcal
    * Protein: ${gizi.proteinG} g
    * Lemak: ${gizi.lemakG} g
    * Karbohidrat: ${gizi.karbohidratG} g
    * Gula: ${gizi.gulaG} g
    * Natrium: ${gizi.natriumG} g

    Vitamin:
    * Vitamin A: ${gizi.vitaminA}%
    * Vitamin C: ${gizi.vitaminC}%
    * Vitamin D: ${gizi.vitaminD}%
    * Vitamin E: ${gizi.vitaminE}%
    * Vitamin K: ${gizi.vitaminK}%
    * Vitamin B Kompleks:
        * Tiamin (B1): ${gizi.vitaminB1Tiamin}%
        * Riboflavin (B2): ${gizi.vitaminB2Riboflavin}%
        * Niasin (B3): ${gizi.vitaminB3Niasin}%
        * Asam Pantotenat (B5): ${gizi.vitaminB5AsamPantotenat}%
        * Piridoksin (B6): ${gizi.vitaminB6Piridoksin}%
        * Asam Folat (B9): ${gizi.vitaminB9AsamFolat}%
        * Kobalamin (B12): ${gizi.vitaminB12Kobalamin}%
    * Biotin: ${gizi.biotin}%

    Mineral:
    * Kalsium: ${gizi.kalsium}%
    * Fosfor: ${gizi.fosfor}%
    * Magnesium: ${gizi.magnesium}%
    * Zat Besi: ${gizi.zatBesi}%
    * Zink: ${gizi.zink}%
    * Kalium: ${gizi.kalium}%
    * Iodium: ${gizi.iodium}%
    * Selenium: ${gizi.selenium}%
    * Tembaga: ${gizi.tembaga}%

    Lainnya:
    * Kolin: ${gizi.kolin}%

    Asam Amino:
    * Isoleusin: ${gizi.isoleusinG ?: "Tidak tersedia"} g
    * Leusin: ${gizi.leusinG ?: "Tidak tersedia"} g
    * Lisin: ${gizi.lisinG ?: "Tidak tersedia"} g
    * Metionin: ${gizi.metioninG ?: "Tidak tersedia"} g
    * Fenilalanin: ${gizi.fenilalaninG ?: "Tidak tersedia"} g
    * Threonin: ${gizi.threoninG ?: "Tidak tersedia"} g
    * Triptofan: ${gizi.triptofanG ?: "Tidak tersedia"} g
    * Valin: ${gizi.valinG ?: "Tidak tersedia"} g
    * Taurin: ${gizi.taurinG ?: "Tidak tersedia"} g
    * Klorida: ${gizi.kloridaG ?: "Tidak tersedia"} g
    """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}