package com.projectprovip.h1eu.giasu.data.profile.api

import com.projectprovip.h1eu.giasu.data.profile.dto.ProfileDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {
    @GET("Profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): ProfileDto
}