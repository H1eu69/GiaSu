package com.projectprovip.h1eu.giasu.domain.course.model

data class RequestedCourse(
    val creationTime: String = "",
    val id: String = "-1",
    val lastModificationTime: String = "",
    val status: String = "",
    val subjectName: String = "",
    val title: String = "",
    val learningMode: String = ""
)

fun RequestedCourse.toLearningCourse() = LearningCourse(
    creationTime, id, lastModificationTime, status, subjectName, title, learningMode
)
