package com.projectprovip.h1eu.giasu.data.course.dto.learningCourseDetailDto

data class LearningCourseDetailDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)