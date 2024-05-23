package com.projectprovip.h1eu.giasu.data.course.dto.learning_course

import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse

data class Value(
    val creationTime: String,
    val id: String,
    val lastModificationTime: String,
    val learningMode: String,
    val status: String,
    val subjectName: String,
    val title: String
)

fun Value.toLearningCourse() = LearningCourse(
    creationTime, id, lastModificationTime, status, subjectName, title, learningMode
)