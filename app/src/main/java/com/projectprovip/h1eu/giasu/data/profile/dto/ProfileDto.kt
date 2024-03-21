package com.projectprovip.h1eu.giasu.data.profile.dto

data class ProfileDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)