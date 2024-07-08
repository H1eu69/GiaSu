package com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto

import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectItem

data class Major(
    val subjectId: Int,
    val subjectName: String
)

fun Major.toSubjectItem() = SubjectItem(
    subjectId, subjectName
)