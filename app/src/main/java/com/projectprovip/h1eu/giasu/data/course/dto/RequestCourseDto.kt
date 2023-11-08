package com.projectprovip.h1eu.giasu.data.course.dto

data class RequestCourseDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean
)