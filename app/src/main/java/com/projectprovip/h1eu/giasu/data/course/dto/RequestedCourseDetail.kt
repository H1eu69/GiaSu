package com.projectprovip.h1eu.giasu.data.course.dto

import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail

data class RequestedCourseDetail(
    val courseId: Int,
    val creationTime: String,
    val description: String,
    val id: Int,
    val isDeleted: Boolean,
    val lastModificationTime: String,
    val learnerContact: String,
    val learnerName: String,
    val requestStatus: String,
    val subjectName: String?,
    val title: String,
    val tutorEmail: String,
    val tutorId: Int,
    val tutorName: String,
    val tutorPhone: String
)

fun RequestedCourseDetail.toRequestedCourseDetail() = RequestedCourseDetail(
    courseId, creationTime, description, id, learnerContact, learnerName, requestStatus, title, subjectName, tutorId, tutorName, tutorPhone
)