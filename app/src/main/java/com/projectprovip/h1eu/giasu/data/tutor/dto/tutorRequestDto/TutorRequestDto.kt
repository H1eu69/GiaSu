package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRequestDto

data class TutorRequestDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean
)