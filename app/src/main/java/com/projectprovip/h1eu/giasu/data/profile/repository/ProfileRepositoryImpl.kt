package com.projectprovip.h1eu.giasu.data.profile.repository

import com.projectprovip.h1eu.giasu.data.profile.api.ProfileApi
import com.projectprovip.h1eu.giasu.data.profile.dto.profileDto.ProfileDto
import com.projectprovip.h1eu.giasu.data.profile.dto.updateProfileDto.UpdateProfileDto
import com.projectprovip.h1eu.giasu.data.profile.dto.updateTutorInfoDto.UpdateTutorInfoDto
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateTutorInfoParams
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository{
    override suspend fun getUserProfile(token: String): ProfileDto {
        return api.getUserProfile(token)
    }

    override suspend fun updateProfile(token: String, params: UpdateProfileParams): UpdateProfileDto {
        return api.updateProfile(token, params)
    }

    override suspend fun updateTutorInfo(
        token: String,
        params: UpdateTutorInfoParams
    ): UpdateTutorInfoDto {
        return api.updateTutorInformation(token, params)
    }
}