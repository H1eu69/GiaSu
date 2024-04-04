package com.projectprovip.h1eu.giasu.data.course.dto.course_by_id

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail

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
    val sectionFee: Int,
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