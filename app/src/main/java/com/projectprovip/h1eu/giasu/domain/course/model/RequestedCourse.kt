package com.projectprovip.h1eu.giasu.domain.course.model

data class RequestedCourse(
    val courseId: Int,
    val creationTime: String,
    val id: Int,
    val requestStatus: String,
    val subjectName: String,
    val title: String
)
