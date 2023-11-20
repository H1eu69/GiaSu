package com.projectprovip.h1eu.giasu.data.course.dto

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse

data class RequestedCourseDtoItem(
    val courseId: Int,
    val creationTime: String,
    val id: Int,
    val lastModificationTime: String,
    val requestStatus: String,
    val subjectName: String,
    val title: String
)

fun RequestedCourseDtoItem.toRequestedCourse() = RequestedCourse(
    courseId,
    creationTime,
    id,
    requestStatus,
    subjectName,
    title
)