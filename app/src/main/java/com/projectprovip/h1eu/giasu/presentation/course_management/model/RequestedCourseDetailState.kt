package com.projectprovip.h1eu.giasu.presentation.course_management.model

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail

data class RequestedCourseDetailState (
    val message: String = "",
    val data: RequestedCourseDetail? = null,
    val isLoading: Boolean = false,
)