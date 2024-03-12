package com.projectprovip.h1eu.giasu.data.course.dto.new_courses

data class NewCoursesDto(
    val displayMessage: String,
    val error: Error,
    val errors: List<Any>,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)