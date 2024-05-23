package com.projectprovip.h1eu.giasu.data.subject.dto

import com.projectprovip.h1eu.giasu.domain.subject.model.Subject

data class Value(
    val description: String,
    val id: Int,
    val name: String
)

fun Value.toSubject() = Subject(
    description, id, name
)