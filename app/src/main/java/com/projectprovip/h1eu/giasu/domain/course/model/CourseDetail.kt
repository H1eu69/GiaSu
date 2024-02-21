package com.projectprovip.h1eu.giasu.domain.course.model

data class CourseDetail(
    val academicLevelRequirement: String = "Dummy academicLevelRequirement",
    val address: String = "Dummy address",
    val chargeFee: Double = -1.0,
    val contactNumber: String = "Dummy contactNumber",
    val description: String = "Dummy description",
    val fee: Double = -1.0,
    val genderRequirement: String = "Dummy genderRequirement",
    val id: Int = -1,
    val learnerGender: String = "Dummy learnerGender",
    val learningMode: String = "Dummy learningMode",
    val minutePerSession: Int = -1,
    val numberOfLearner: Int = -1,
    val sessionPerWeek: Int = -1,
    val status: String = "Dummy status",
    val subjectId: Int = -1,
    val subjectName: String = "Dummy subjectName",
    val title: String = "Dummy title",
    val creationTime: String = "2023-12-26T22:56:45"
)