package com.projectprovip.h1eu.giasu.presentation.tutor.model

import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor

data class TutorListState(
    val isLoading: Boolean = false,
    val data: List<Tutor> = emptyList(),
    val error: String = ""
)