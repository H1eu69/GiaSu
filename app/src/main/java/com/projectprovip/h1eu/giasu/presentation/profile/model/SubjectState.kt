package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.Major

data class SubjectState(
    val isLoading: Boolean = false,
    val data: List<SubjectItem> = emptyList(),
    val error: String = ""
)

data class SubjectItem(
    val id: Int = -1,
    val name: String = "dummy name",
    var isSelected: Boolean = false
) {

}

fun SubjectItem.toMajor() = Major(
    id, name
)