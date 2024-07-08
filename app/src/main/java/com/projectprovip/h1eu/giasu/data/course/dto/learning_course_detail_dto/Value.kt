package com.projectprovip.h1eu.giasu.data.course.dto.learning_course_detail_dto

import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourseDetail

data class Value(
    val address: String,
    val chargeFee: Double,
    val creationTime: String,
    val description: String,
    val id: String,
    val lastModificationTime: String,
    val learningMode: String,
    val sectionFee: Double,
    val sessionDuration: Int,
    val sessionPerWeek: Int,
    val status: String,
    val subjectName: String,
    val title: String,
    val tutorContact: String,
    val tutorEmail: String,
    val tutorId: String,
    val tutorName: String
)

fun Value.toLearningCourseDetail() = LearningCourseDetail(
    address,
    chargeFee,
    creationTime,
    description,
    id,
    lastModificationTime,
    learningMode,
    sectionFee,
    sessionDuration,
    sessionPerWeek,
    status,
    subjectName,
    title,
    tutorContact,
    tutorEmail,
    tutorId,
    tutorName
)