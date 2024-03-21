package com.projectprovip.h1eu.giasu.domain.profile.repository

import com.projectprovip.h1eu.giasu.data.profile.dto.ProfileDto

interface ProfileRepository {
    suspend fun getUserProfile(token: String) : ProfileDto
}