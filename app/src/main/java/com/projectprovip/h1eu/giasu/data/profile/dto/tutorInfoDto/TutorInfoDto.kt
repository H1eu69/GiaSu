package com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto

data class TutorInfoDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)