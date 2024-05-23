package com.projectprovip.h1eu.giasu.data.profile.api

import com.projectprovip.h1eu.giasu.data.profile.dto.profileDto.ProfileDto
import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.TutorInfoDto
import com.projectprovip.h1eu.giasu.data.profile.dto.updateProfileDto.UpdateProfileDto
import com.projectprovip.h1eu.giasu.data.profile.dto.updateTutorInfoDto.UpdateTutorInfoDto
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateTutorInfoParams
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProfileApi {
    @GET("profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): ProfileDto

    @PUT("profile/edit")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body params: UpdateProfileParams
    ): UpdateProfileDto

    @PUT("Profile/tutor-information/edit")
    suspend fun updateTutorInformation(
        @Header("Authorization") token: String,
        @Body params: UpdateTutorInfoParams
    ): UpdateTutorInfoDto

    @GET("profile/tutor-information")
    suspend fun getTutorInfo(
        @Header("Authorization") token: String,
    ): TutorInfoDto

}
