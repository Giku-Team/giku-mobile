package com.mobile.giku.model.remote.child

import com.mobile.giku.BuildConfig
import com.mobile.giku.model.remote.generic.GenericResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ChildApiService {

    @Multipart
    @POST(BuildConfig.CHILDREN_URL)
    suspend fun addChildProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Part("name") name: MultipartBody.Part,
        @Part("dateOfBirth") dateOfBirth: MultipartBody.Part,
        @Part("gender") gender: MultipartBody.Part,
        @Part("weight") weight: MultipartBody.Part,
        @Part("height") height: MultipartBody.Part,
        @Part("bloodType") bloodType: MultipartBody.Part,
        @Part("fatherHeight") fatherHeight: MultipartBody.Part,
        @Part("motherHeight") motherHeight: MultipartBody.Part,
        @Part("allergies") allergies: MultipartBody.Part,
        @Part photo: MultipartBody.Part
    ): Response<GenericResponse>

    @GET(BuildConfig.CHILDREN_URL)
    suspend fun getChildren(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<GetChildrenResponse>
}