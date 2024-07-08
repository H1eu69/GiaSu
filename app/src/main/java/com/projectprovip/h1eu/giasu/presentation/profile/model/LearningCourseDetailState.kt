package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourseDetail

data class LearningCourseDetailState(
    val isLoading: Boolean = false,
    val data: LearningCourseDetail = LearningCourseDetail(),
    val message: String = ""
)