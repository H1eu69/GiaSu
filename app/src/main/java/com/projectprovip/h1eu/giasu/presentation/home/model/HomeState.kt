package com.projectprovip.h1eu.giasu.presentation.home.model

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor

data class HomeState(
    var isLoading: Boolean = false,
    var data: List<CourseDetail> = emptyList(),
    var error: String? = null
)

data class TutorState(
    var isLoading: Boolean = false,
    var data: List<Tutor> = emptyList(),
    var error: String? = null
)