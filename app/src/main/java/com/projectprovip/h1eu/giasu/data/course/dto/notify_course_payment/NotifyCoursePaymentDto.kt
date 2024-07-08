package com.projectprovip.h1eu.giasu.data.course.dto.notify_course_payment

data class NotifyCoursePaymentDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean
)