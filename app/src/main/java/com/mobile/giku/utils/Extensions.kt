package com.mobile.giku.utils

import com.mobile.giku.model.remote.child.Allergy
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String.toMultipartBodyPart(name: String): MultipartBody.Part {
    val requestBody = this.toRequestBody(MultipartBody.FORM)
    return MultipartBody.Part.createFormData(name, this, requestBody)
}

fun Double.toMultipartBodyPart(name: String): MultipartBody.Part {
    val requestBody = this.toString().toRequestBody(MultipartBody.FORM)
    return MultipartBody.Part.createFormData(name, this.toString(), requestBody)
}

fun Int.toMultipartBodyPart(name: String): MultipartBody.Part {
    val requestBody = this.toString().toRequestBody(MultipartBody.FORM)
    return MultipartBody.Part.createFormData(name, this.toString(), requestBody)
}

fun List<Allergy>.toMultipartBodyPart(name: String): MultipartBody.Part {
    val requestBody = this.joinToString(",").toRequestBody(MultipartBody.FORM)
    return MultipartBody.Part.createFormData(name, this.joinToString(","), requestBody)
}
