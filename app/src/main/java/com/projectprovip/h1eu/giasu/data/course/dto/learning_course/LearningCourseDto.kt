package com.projectprovip.h1eu.giasu.data.course.dto.learning_course

data class LearningCourseDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: List<Value>
)