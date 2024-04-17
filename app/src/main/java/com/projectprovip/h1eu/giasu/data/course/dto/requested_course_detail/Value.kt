package com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail

data class Value(
    val address: String,
    val chargeFee: Double,
    val creationTime: String,
    val description: String,
    val id: String,
    val lastModificationTime: String,
    val learningMode: String,
    val sectionFee: Int,
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

fun Value.toRequestedCourseDetail() = RequestedCourseDetail(
    creationTime = creationTime, description = description, id = id, subjectName = subjectName,
    tutorId = tutorId, tutorName = tutorName, tutorPhone = tutorContact, title = title, requestStatus = status
)