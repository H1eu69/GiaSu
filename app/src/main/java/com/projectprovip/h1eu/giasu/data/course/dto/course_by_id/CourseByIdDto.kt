package com.projectprovip.h1eu.giasu.data.course.dto.course_by_id

data class CourseByIdDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)