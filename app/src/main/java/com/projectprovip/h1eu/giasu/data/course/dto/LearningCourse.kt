package com.projectprovip.h1eu.giasu.data.course.dto

data class LearningCourse(
    val creationTime: String,
    val id: Int,
    val lastModificationTime: String,
    val status: String,
    val subjectName: String,
    val title: String
)

fun LearningCourse.toLearningCourse() = com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse(
    creationTime, id, lastModificationTime, status, subjectName, title
)