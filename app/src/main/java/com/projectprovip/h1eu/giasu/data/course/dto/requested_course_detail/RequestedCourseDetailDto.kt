package com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail

data class RequestedCourseDetailDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: Value
)