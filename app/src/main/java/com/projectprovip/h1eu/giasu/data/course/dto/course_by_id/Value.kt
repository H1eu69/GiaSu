package com.projectprovip.h1eu.giasu.data.course.dto.course_by_id

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentDetailModel

data class Value(
    val academicLevelRequirement: String,
    val address: String,
    val chargeFee: Double,
    val creationTime: String,
    val description: String,
    val genderRequirement: String,
    val id: String,
    val lastModificationTime: String,
    val learnerGender: String,
    val learnerName: String,
    val learningMode: String,
    val numberOfLearner: Int,
    val sectionFee: Double,
    val sessionDuration: Int,
    val sessionPerWeek: Int,
    val status: String,
    val subjectId: Int,
    val subjectName: String,
    val title: String
)

fun Value.toCourseDetail() = CourseDetail(
    academicLevelRequirement,
    address,
    chargeFee,
    creationTime,
    description,
    sectionFee,
    genderRequirement,
    id,
    learnerGender,
    learningMode,
    sessionDuration,
    numberOfLearner,
    sessionPerWeek,
    status,
    subjectId, subjectName, title
)

fun Value.toCoursePaymentDetail() = CoursePaymentDetailModel(
    chargeFee = chargeFee,
    fee = sectionFee,
    subjectName = subjectName,
    title = title,
    creationTime = creationTime,
    learningMode = learningMode,
    description = description,
    sessionPerWeek = sessionPerWeek,
    sessionDuration = sessionDuration,
    numberOfLearner = numberOfLearner,
    subjectId = subjectId
)
