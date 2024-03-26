package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourseDetail

data class CreateCourseState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val subjects: List<SubjectItem> = emptyList(),
    val message: String = ""
)