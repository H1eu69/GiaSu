package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse

data class ReviewTutorState (
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val message: String = ""
)