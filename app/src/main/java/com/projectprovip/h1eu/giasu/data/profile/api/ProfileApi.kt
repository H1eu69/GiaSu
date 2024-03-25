package com.projectprovip.h1eu.giasu.data.profile.api

import com.projectprovip.h1eu.giasu.data.profile.dto.profileDto.ProfileDto
import com.projectprovip.h1eu.giasu.data.profile.dto.updateProfileDto.UpdateProfileDto
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProfileApi {
    @GET("Profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): ProfileDto

    @PUT("Profile/edit")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body params: UpdateProfileParams
    ): UpdateProfileDto
}
