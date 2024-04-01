package com.projectprovip.h1eu.giasu.domain.course.model

import androidx.annotation.Keep

//data class LearningCourse(
//    val academicLevelRequirement: String,
//    val address: String,
//    val chargeFee: Int,
//    val contactNumber: String,
//    val creationTime: String,
//    val description: String,
//    val fee: Int,
//    val genderRequirement: String,
//    val id: Int,
//    val lastModificationTime: String,
//    val learnerGender: String,
//    val learningMode: String,
//    val minutePerSession: Int,
//    val numberOfLearner: Int,
//    val sessionPerWeek: Int,
//    val status: String,
//    val subjectId: Int,
//    val subjectName: String,
//    val title: String,
//    val tutorId: Int,
//    val tutorName: String,
//    val tutorPhoneNumber: String
//)

@Keep
data class LearningCourse(
    val creationTime: String = "",
    val id: String = "-1",
    val lastModificationTime: String = "",
    val status: String = "",
    val subjectName: String = "",
    val title: String = "",
    val learningMode: String = ""
)
