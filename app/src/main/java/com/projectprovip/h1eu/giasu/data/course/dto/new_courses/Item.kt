package com.projectprovip.h1eu.giasu.data.course.dto.new_courses

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail


data class Item(
    val academicLevelRequirement: String,
    val address: String,
    val chargeFee: Double,
    val contactNumber: String,
    val creationTime: String,
    val description: String,
    val fee: Double,
    val genderRequirement: String,
    val id: String,
    val lastModificationTime: String,
    val learnerGender: String,
    val learningMode: String,
    val numberOfLearner: Int,
    val sessionDuration: Int,
    val sessionPerWeek: Int,
    val status: String,
    val subjectId: Int,
    val subjectName: String,
    val title: String
)

fun Item.toNewCourse() = CourseDetail(
    academicLevelRequirement,
    address,
    chargeFee,
    creationTime,
    description,
    fee,
    genderRequirement,
    id,
    learnerGender,
    learningMode,
    sessionDuration,
    numberOfLearner,
    sessionPerWeek,
    status,
    subjectId,
    subjectName,
    title,
)