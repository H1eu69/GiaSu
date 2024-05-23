package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto

data class TutorDetailDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)