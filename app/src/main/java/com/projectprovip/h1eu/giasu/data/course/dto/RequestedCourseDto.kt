package com.projectprovip.h1eu.giasu.data.course.dto

import com.google.gson.annotations.SerializedName

data class RequestedCourseDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    @SerializedName(value = "value")
    val requestedCourseDtoItem: List<RequestedCourseDtoItem>
)
