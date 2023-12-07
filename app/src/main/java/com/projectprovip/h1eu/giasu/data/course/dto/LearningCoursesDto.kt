package com.projectprovip.h1eu.giasu.data.course.dto

import com.google.gson.annotations.SerializedName

data class LearningCoursesDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isSuccess: Boolean,
    val pageIndex: Int,
    val totalItems: Int,
    val totalPages: Int,
    @SerializedName(value = "value")
    val learningCourseItem: List<LearningCourseItem>
)