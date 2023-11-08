package com.projectprovip.h1eu.giasu.presentation.home.model

data class CourseRegisterState (
    val isLoading : Boolean = false,
    val message: String = "",
    val error: Boolean = false,
    val isSuccess: Boolean = false
)