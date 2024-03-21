package com.projectprovip.h1eu.giasu.data.profile.repository

import com.projectprovip.h1eu.giasu.data.profile.api.ProfileApi
import com.projectprovip.h1eu.giasu.data.profile.dto.ProfileDto
import com.projectprovip.h1eu.giasu.domain.profile.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository{
    override suspend fun getUserProfile(token: String): ProfileDto {
        return api.getUserProfile(token)
    }
}