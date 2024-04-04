package com.projectprovip.h1eu.giasu.presentation.profile.model

data class CreateCourseState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val subjects: List<SubjectItem> = emptyList(),
    val message: String = ""
)