package com.projectprovip.h1eu.giasu.data.classes.dto

import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.domain.classes.model.NewClass

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

fun CourseInformationDtoItem.toNewClass() = NewClass(
    title = title,
    id = id,
    creationTime = DateFormat.DD_MM_YYYY(creationTime),
    fee = fee,
    sessionPerWeek = sessionPerWeek,
    minutePerSession = minutePerSession,
    address = address,
    description = description
)