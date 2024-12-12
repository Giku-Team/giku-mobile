package com.mobile.giku.model.remote.nutrient

import com.google.gson.annotations.SerializedName

data class NutritionPredictionResponse(
    val code: Int,
    @SerializedName("ConfidenceScore")
    val confidenceScore: String,
    @SerializedName("KandunganGizi")
    val kandunganGizi: KandunganGizi,
    val message: String,
    @SerializedName("NamaMakanan")
    val namaMakanan: String
)

data class KandunganGizi(
    @SerializedName("Biotin (%)")
    val biotin: Double,
    @SerializedName("Fenilalanin (g)")
    val fenilalaninG: Double,
    @SerializedName("Fosfor (%)")
    val fosfor: Double,
    @SerializedName("Gula (g)")
    val gulaG: Double,
    @SerializedName("Iodium (%)")
    val iodium: Double,
    @SerializedName("Isoleusin (g)")
    val isoleusinG: Double,
    @SerializedName("Kalium (%)")
    val kalium: Double,
    @SerializedName("Kalori (kcal)")
    val kaloriKcal: Double,
    @SerializedName("Kalsium (%)")
    val kalsium: Double,
    @SerializedName("Karbohidrat (g)")
    val karbohidratG: Double,
    @SerializedName("Klorida (g)")
    val kloridaG: Double,
    @SerializedName("Kolin (%)")
    val kolin: Double,
    @SerializedName("Lemak (g)")
    val lemakG: Double,
    @SerializedName("Leusin (g)")
    val leusinG: Double,
    @SerializedName("Lisin (g)")
    val lisinG: Double,
    @SerializedName("Magnesium (%)")
    val magnesium: Double,
    @SerializedName("Metionin (g)")
    val metioninG: Double,
    @SerializedName("Natrium (g)")
    val natriumG: Double,
    @SerializedName("Protein (g)")
    val proteinG: Double,
    @SerializedName("Selenium (%)")
    val selenium: Double,
    @SerializedName("Taurin (g)")
    val taurinG: Double,
    @SerializedName("Tembaga (%)")
    val tembaga: Double,
    @SerializedName("Threonin (g)")
    val threoninG: Double,
    @SerializedName("Triptofan (g)")
    val triptofanG: Double,
    @SerializedName("Valin (g)")
    val valinG: Double,
    @SerializedName("Vitamin A (%)")
    val vitaminA: Double,
    @SerializedName("Vitamin B12 (Kobalamin) (%)")
    val vitaminB12Kobalamin: Double,
    @SerializedName("Vitamin B1 (Tiamin) (%)")
    val vitaminB1Tiamin: Double,
    @SerializedName("Vitamin B2 (Riboflavin) (%)")
    val vitaminB2Riboflavin: Double,
    @SerializedName("Vitamin B3 (Niasin) (%)")
    val vitaminB3Niasin: Double,
    @SerializedName("Vitamin B5 (Asam Pantotenat) (%)")
    val vitaminB5AsamPantotenat: Double,
    @SerializedName("Vitamin B6 (Piridoksin) (%)")
    val vitaminB6Piridoksin: Double,
    @SerializedName("Vitamin B9 (Asam Folat) (%)")
    val vitaminB9AsamFolat: Double,
    @SerializedName("Vitamin C (%)")
    val vitaminC: Double,
    @SerializedName("Vitamin D (%)")
    val vitaminD: Double,
    @SerializedName("Vitamin E (%)")
    val vitaminE: Double,
    @SerializedName("Vitamin K (%)")
    val vitaminK: Double,
    @SerializedName("Zat Besi (%)")
    val zatBesi: Double,
    @SerializedName("Zink (%)")
    val zink: Double
)