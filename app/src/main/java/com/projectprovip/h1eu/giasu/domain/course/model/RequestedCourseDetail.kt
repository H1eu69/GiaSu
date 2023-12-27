package com.projectprovip.h1eu.giasu.domain.course.model

data class RequestedCourseDetail(
    val courseId: Int,
    val creationTime: String,
    val description: String,
    val id: Int,
    val learnerContact: String,
    val learnerName: String,
    val requestStatus: String,
    val subjectName: String,
    val title: String,
    val tutorId: Int,
    val tutorName: String,
    val tutorPhone: String
)