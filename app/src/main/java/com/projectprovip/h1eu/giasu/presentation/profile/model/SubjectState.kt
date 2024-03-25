package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.profile.model.Profile

data class SubjectState(
    val isLoading: Boolean = false,
    val data: List<SubjectItem> = emptyList(),
    val error: String = ""
)

data class SubjectItem(
    val description: String = "",
    val id: Int = -1,
    val name: String = "dummy name",
    var isSelected: Boolean = false
)
