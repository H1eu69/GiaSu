package com.projectprovip.h1eu.giasu.data.course.dto.request_course

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse

data class Value(
    val creationTime: String,
    val id: String,
    val lastModificationTime: String,
    val learningMode: String,
    val status: String,
    val subjectName: String,
    val title: String
)

fun Value.toRequestedCourse() = RequestedCourse(
    creationTime, id,  status, subjectName, title
)