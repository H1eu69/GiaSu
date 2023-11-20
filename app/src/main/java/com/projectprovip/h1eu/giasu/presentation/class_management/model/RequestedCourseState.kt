package com.projectprovip.h1eu.giasu.presentation.class_management.model

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse

data class RequestedCourseState(
    val message: String = "",
    val data: List<RequestedCourse> = emptyList(),
    val isLoading: Boolean = false,
)