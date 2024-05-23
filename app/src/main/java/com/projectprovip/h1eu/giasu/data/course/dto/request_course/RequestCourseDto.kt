package com.projectprovip.h1eu.giasu.data.course.dto.request_course

data class RequestCourseDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: List<Value>
)