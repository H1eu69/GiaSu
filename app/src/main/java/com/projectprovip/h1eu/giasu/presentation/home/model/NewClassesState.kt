package com.projectprovip.h1eu.giasu.presentation.home.model

import com.projectprovip.h1eu.giasu.domain.classes.model.NewClass

data class NewClassesState(
    var isLoading: Boolean = false,
    var data: List<NewClass> = emptyList(),
    var error: String? = null
)
