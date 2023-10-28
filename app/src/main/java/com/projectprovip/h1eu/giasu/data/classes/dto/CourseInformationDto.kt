package com.projectprovip.h1eu.giasu.data.classes.dto

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail

data class CourseInformationDtoItem(
    val academicLevelRequirement: String,
    val address: String,
    val chargeFee: Double,
    val contactNumber: String,
    val creationTime: String,
    val description: String,
    val fee: Double,
    val genderRequirement: String,
    val id: Int,
    val lastModificationTime: String,
    val learnerGender: String,
    val learningMode: String,
    val minutePerSession: Int,
    val numberOfLearner: Int,
    val sessionPerWeek: Int,
    val status: String,
    val subjectId: Int,
    val subjectName: String,
    val title: String
)

fun CourseInformationDtoItem.toNewClassDetail() = CourseDetail(
    title = title,
    id = id,
    fee = fee,
    sessionPerWeek = sessionPerWeek,
    minutePerSession = minutePerSession,
    address = address,
    description = description,
    academicLevelRequirement = academicLevelRequirement,
    chargeFee = chargeFee,
    contactNumber = contactNumber,
    genderRequirement = genderRequirement,
    learnerGender = learnerGender,
    learningMode = learningMode,
    numberOfLearner = numberOfLearner,
    status = status,
    subjectId = subjectId,
    subjectName = subjectName,
    creationTime = creationTime
)