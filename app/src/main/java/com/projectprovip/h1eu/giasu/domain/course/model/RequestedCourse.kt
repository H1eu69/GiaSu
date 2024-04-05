package com.projectprovip.h1eu.giasu.domain.course.model

data class RequestedCourse(
    val creationTime: String,
    val id: String,
    val requestStatus: String,
    val subjectName: String,
    val title: String
)
