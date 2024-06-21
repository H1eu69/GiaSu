package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRecDto

data class TutorRecDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: List<Value>
)