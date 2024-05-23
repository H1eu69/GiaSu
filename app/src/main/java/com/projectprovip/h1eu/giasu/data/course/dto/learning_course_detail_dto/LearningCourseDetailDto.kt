package com.projectprovip.h1eu.giasu.data.course.dto.learning_course_detail_dto

data class LearningCourseDetailDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)