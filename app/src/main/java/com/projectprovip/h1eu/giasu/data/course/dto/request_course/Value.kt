package com.projectprovip.h1eu.giasu.data.course.dto.request_course

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse

data class Value(
    val courseId: String,
    val creationTime: String,
    val id: String,
    val lastModificationTime: String,
    val requestStatus: String,
    val subjectName: String,
    val title: String
)

fun Value.toRequestedCourse() = RequestedCourse(
    creationTime,
    id,
    lastModificationTime,
    requestStatus,
    subjectName,
    title
)