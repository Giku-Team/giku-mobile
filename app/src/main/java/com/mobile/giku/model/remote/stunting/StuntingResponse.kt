package com.mobile.giku.model.remote.stunting

data class StuntingResponse(
    val code: Int,
    val message: String,
    val confidence: String,
    val data: StuntingData
)

data class StuntingData(
    val status: String,
    val recommendation: String
)