package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.profile.model.Profile
import com.projectprovip.h1eu.giasu.domain.profile.model.TutorInfo

data class GetProfileState(
    val isLoading: Boolean = false,
    val data: Profile = Profile(),
    val error: String = ""
)
data class GetTutorInfoState(
    val isLoading: Boolean = false,
    val data: TutorInfo = TutorInfo(),
    val error: String = ""
)
data class UpdateProfileState(
    val isLoading: Boolean = false,
    val isUpdateSuccess: Boolean = false,
    val data: MiniProfile = MiniProfile(),
    val error: String = ""
)

data class UpdateTutorInfoState(
    val isLoading: Boolean = false,
    val isUpdateProfileDone: Boolean = false,
    val isUpdateTutorDone: Boolean = false,
    val data: Profile = Profile(),
    val error: String = ""
)

data class MiniProfile(
    val avatar: String = "",
    val fullName: String = "",
    val email: String = "",
)

