package com.projectprovip.h1eu.giasu.presentation.home.model

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail

data class CourseDetailState(
    var isLoading: Boolean = false,
    var data: List<CourseDetail> = emptyList(),
    var error: String? = null
)