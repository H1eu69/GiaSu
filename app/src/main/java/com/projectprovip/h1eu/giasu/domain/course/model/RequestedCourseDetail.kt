package com.projectprovip.h1eu.giasu.domain.course.model

data class RequestedCourseDetail(
    val courseId: Int = -1,
    val creationTime: String = "2023-12-26T22:56:45",
    val description: String = "dummy description",
    val id: Int = -1,
    val learnerContact: String= "dummy description",
    val learnerName: String= "dummy description",
    val requestStatus: String= "dummy description",
    val subjectName: String= "dummy description",
    val title: String= "dummy description",
    val tutorId: Int = -1,
    val tutorName: String= "dummy description",
    val tutorPhone: String= "dummy description"
)