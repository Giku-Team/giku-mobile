package com.mobile.giku.repository.child

import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.child.AddChildRequest
import com.mobile.giku.model.remote.child.ChildApiService
import com.mobile.giku.model.remote.child.GetChildrenResponse
import com.mobile.giku.model.remote.generic.GenericResponse
import com.mobile.giku.utils.toMultipartBodyPart
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ChildRepository(
    private val apiService: ChildApiService,
    private val dataStore: AuthDataStore
) {

    suspend fun addChildProfile(
        userId: String,
        addChildRequest: AddChildRequest
    ): Response<GenericResponse> {
        val token = dataStore.getToken().first() ?: return Response.error(401,
            "Unauthorized".toResponseBody(null)
        )

        return apiService.addChildProfile(
            token = token,
            userId = userId,
            name = addChildRequest.name.toMultipartBodyPart("name"),
            dateOfBirth = addChildRequest.dateOfBirth.toMultipartBodyPart("dateOfBirth"),
            gender = addChildRequest.gender.toMultipartBodyPart("gender"),
            weight = addChildRequest.weight.toMultipartBodyPart("weight"),
            height = addChildRequest.height.toMultipartBodyPart("height"),
            bloodType = addChildRequest.bloodType.toMultipartBodyPart("bloodType"),
            fatherHeight = addChildRequest.fatherHeight.toMultipartBodyPart("fatherHeight"),
            motherHeight = addChildRequest.motherHeight.toMultipartBodyPart("motherHeight"),
            allergies = addChildRequest.allergies.toMultipartBodyPart("allergies"),
            photo = addChildRequest.photo?.toMultipartBodyPart("photo") ?: MultipartBody.Part.createFormData("photo", "")
        )
    }

    suspend fun getChildren(userId: String): Response<GetChildrenResponse> {

        val token = dataStore.getToken().first() ?: return Response.error(401,
            "Unauthorized".toResponseBody(null)
        )
        return apiService.getChildren(token = token, userId = userId)
    }
}
