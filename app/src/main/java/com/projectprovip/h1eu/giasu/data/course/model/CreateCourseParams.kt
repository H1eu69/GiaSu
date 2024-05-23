package com.projectprovip.h1eu.giasu.data.course.model

data class CreateCourseParams(
    val academicLevelRequirement: Int,
    val address: String,
    val contactNumber: String,
    val description: String,
    val fee: Double,
    val genderRequirement: Int,
    val learnerGender: Int,
    val learnerName: String,
    val learningMode: Int,
    val minutePerSession: Int,
    val numberOfLearner: Int,
    val sessionPerWeek: Int,
    val subjectId: Int,
    val title: String
)