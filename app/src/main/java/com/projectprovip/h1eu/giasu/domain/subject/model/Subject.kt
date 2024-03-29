package com.projectprovip.h1eu.giasu.domain.subject.model

import com.projectprovip.h1eu.giasu.presentation.profile.model.SubjectItem

data class Subject(
    val description: String,
    val id: Int,
    val name: String
)

fun Subject.toSubjectItem() = SubjectItem(
     id, name
)