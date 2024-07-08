package com.projectprovip.h1eu.giasu.data.course.dto

data class RegisterCourseDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean
)