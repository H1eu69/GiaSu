package com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail

data class Value(
    val courseId: String,
    val description: String,
    val id: String,
    val learnerContact: String,
    val learnerName: String,
    val requestStatus: String,
    val subjectName: String,
    val title: String,
    val tutorEmail: String,
    val tutorId: String,
    val tutorName: String,
    val tutorPhone: String
)

fun Value.toRequestedCourseDetail() = RequestedCourseDetail(
    description = description,
    id = id,
    subjectName = subjectName,
    tutorId = tutorId,
    tutorName = tutorName,
    tutorPhone = tutorPhone,
    title = title,
    requestStatus = requestStatus,
    learnerName = learnerName,
    learnerContact = learnerContact,
)