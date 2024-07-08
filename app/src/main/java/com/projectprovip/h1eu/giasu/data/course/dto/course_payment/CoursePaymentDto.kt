package com.projectprovip.h1eu.giasu.data.course.dto.course_payment

data class CoursePaymentDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: List<Value>
)