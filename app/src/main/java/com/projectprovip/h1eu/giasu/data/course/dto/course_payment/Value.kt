package com.projectprovip.h1eu.giasu.data.course.dto.course_payment

import CoursePayment


data class Value(
    val code: String,
    val courseId: String,
    val courseTitle: String,
    val paymentId: String,
    val paymentStatus: String
)

fun Value.toCoursePayment() = CoursePayment(
    code, courseId, courseTitle, paymentId, paymentStatus
)