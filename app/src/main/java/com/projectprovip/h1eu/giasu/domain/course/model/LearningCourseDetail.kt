package com.projectprovip.h1eu.giasu.domain.course.model

data class LearningCourseDetail(
    val address: String = "",
    val chargeFee: Double = -1.0,
    val creationTime: String = "",
    val description: String = "",
    val id: String = "",
    val lastModificationTime: String = "",
    val learningMode: String = "",
    val sectionFee: Int= -1,
    val sessionDuration: Int= -1,
    val sessionPerWeek: Int= -1,
    val status: String = "",
    val subjectName: String = "",
    val title: String = "",
    val tutorContact: String = "",
    val tutorEmail: String = "",
    val tutorId: String = "",
    val tutorName: String = ""
)