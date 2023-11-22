package com.projectprovip.h1eu.giasu.data.course.dto

import com.google.gson.annotations.SerializedName
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail

data class RequestedCourseDetailDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    @SerializedName(value = "value")
    val requestedCourseDetail: RequestedCourseDetail
)