package com.projectprovip.h1eu.giasu.data.classes.dto

import com.projectprovip.h1eu.giasu.common.DateFormat
import com.projectprovip.h1eu.giasu.domain.classes.model.NewClass

data class Value(
    val academicLevel: Int,
    val address: String,
    val chargeFee: Int,
    val contactNumber: String,
    val creationTime: String,
    val creatorUserId: Any,
    val deleterUserId: Any,
    val deletionTime: Any,
    val description: String,
    val fee: Int,
    val genderRequirement: Int,
    val id: String,
    val isDeleted: Boolean,
    val lastModificationTime: String,
    val lastModifierUserId: Any,
    val learnerGender: Int,
    val learnerId: String,
    val learningMode: Int,
    val minutePerSession: Int,
    val numberOfLearner: Int,
    val sessionPerWeek: Int,
    val status: Int,
    val subjectId: String,
    val subjectName: String,
    val title: String
)

fun Value.toNewClass() = NewClass(
    title = title,
    id = id,
    creationTime = DateFormat.DD_MM_YYYY(creationTime),
    fee = fee,
    sessionPerWeek = sessionPerWeek,
    minutePerSession = minutePerSession,
    address = address,
    description = description
)