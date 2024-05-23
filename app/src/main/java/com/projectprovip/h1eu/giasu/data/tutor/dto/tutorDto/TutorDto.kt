package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto

data class TutorDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)