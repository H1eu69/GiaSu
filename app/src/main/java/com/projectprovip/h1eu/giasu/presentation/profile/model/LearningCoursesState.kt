package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse

data class LearningCoursesState(
    val isLoading: Boolean = false,
    val data: List<LearningCourse> = emptyList(),
    val error: String = ""
)