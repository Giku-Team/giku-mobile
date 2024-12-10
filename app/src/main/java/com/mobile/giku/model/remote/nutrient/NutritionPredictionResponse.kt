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
    val biotin: Int,
    @SerializedName("Fenilalanin (g)")
    val fenilalaninG: Int,
    @SerializedName("Fosfor (%)")
    val fosfor: Int,
    @SerializedName("Gula (g)")
    val gulaG: Int,
    @SerializedName("Iodium (%)")
    val iodium: Int,
    @SerializedName("Isoleusin (g)")
    val isoleusinG: Int,
    @SerializedName("Kalium (%)")
    val kalium: Int,
    @SerializedName("Kalori (kcal)")
    val kaloriKcal: Int,
    @SerializedName("Kalsium (%)")
    val kalsium: Int,
    @SerializedName("Karbohidrat (g)")
    val karbohidratG: Int,
    @SerializedName("Klorida (g)")
    val kloridaG: Int,
    @SerializedName("Kolin (%)")
    val kolin: Int,
    @SerializedName("Lemak (g)")
    val lemakG: String,
    @SerializedName("Leusin (g)")
    val leusinG: Int,
    @SerializedName("Lisin (g)")
    val lisinG: Int,
    @SerializedName("Magnesium (%)")
    val magnesium: Int,
    @SerializedName("Metionin (g)")
    val metioninG: Int,
    @SerializedName("Natrium (g)")
    val natriumG: String,
    @SerializedName("Protein (g)")
    val proteinG: Int,
    @SerializedName("Selenium (%)")
    val selenium: Int,
    @SerializedName("Taurin (g)")
    val taurinG: Int,
    @SerializedName("Tembaga (%)")
    val tembaga: Int,
    @SerializedName("Threonin (g)")
    val threoninG: Int,
    @SerializedName("Triptofan (g)")
    val triptofanG: Int,
    @SerializedName("Valin (g)")
    val valinG: Int,
    @SerializedName("Vitamin A (%)")
    val vitaminA: Int,
    @SerializedName("Vitamin B12 (Kobalamin) (%)")
    val vitaminB12Kobalamin: Int,
    @SerializedName("Vitamin B1 (Tiamin) (%)")
    val vitaminB1Tiamin: Int,
    @SerializedName("Vitamin B2 (Riboflavin) (%)")
    val vitaminB2Riboflavin: Int,
    @SerializedName("Vitamin B3 (Niasin) (%)")
    val vitaminB3Niasin: Int,
    @SerializedName("Vitamin B5 (Asam Pantotenat) (%)")
    val vitaminB5AsamPantotenat: Int,
    @SerializedName("Vitamin B6 (Piridoksin) (%)")
    val vitaminB6Piridoksin: Int,
    @SerializedName("Vitamin B9 (Asam Folat) (%)")
    val vitaminB9AsamFolat: Int,
    @SerializedName("Vitamin C (%)")
    val vitaminC: Int,
    @SerializedName("Vitamin D (%)")
    val vitaminD: Int,
    @SerializedName("Vitamin E (%)")
    val vitaminE: Int,
    @SerializedName("Vitamin K (%)")
    val vitaminK: Int,
    @SerializedName("Zat Besi (%)")
    val zatBesi: Int,
    @SerializedName("Zink (%)")
    val zink: Int
)