package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.profile.model.Profile

data class GetProfileState(
    val isLoading: Boolean = false,
    val data: Profile = Profile(),
    val error: String = ""
)

data class UpdateProfileState(
    val isLoading: Boolean = false,
    val data: Profile = Profile(),
    val error: String = ""
)

data class UpdateTutorInfoState(
    val isLoading: Boolean = false,
    val isUpdateProfileDone: Boolean = false,
    val isUpdateTutorDone: Boolean = false,
    val data: Profile = Profile(),
    val error: String = ""
)