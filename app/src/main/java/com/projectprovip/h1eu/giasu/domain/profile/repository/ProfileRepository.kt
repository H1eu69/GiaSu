package com.projectprovip.h1eu.giasu.domain.profile.repository

import com.projectprovip.h1eu.giasu.data.profile.dto.profileDto.ProfileDto
import com.projectprovip.h1eu.giasu.data.profile.dto.updateProfileDto.UpdateProfileDto
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams

interface ProfileRepository {
    suspend fun getUserProfile(token: String) : ProfileDto

    suspend fun updateProfile(token: String, params: UpdateProfileParams) : UpdateProfileDto
}