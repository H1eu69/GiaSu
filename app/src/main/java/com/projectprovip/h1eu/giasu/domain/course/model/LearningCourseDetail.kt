package com.projectprovip.h1eu.giasu.domain.course.model

import com.projectprovip.h1eu.giasu.presentation.profile.model.LearningCourseDetailState

data class LearningCourseDetail(
    val academicLevelRequirement: String = "",
    val address: String = "",
    val chargeFee: Double = 0.0,
    val contactNumber: String = "",
    val creationTime: String = "",
    val description: String = "",
    val fee: Double = 0.0,
    val genderRequirement: String = "",
    val id: Int = -1,
    val lastModificationTime: String = "",
    val learnerGender: String = "",
    val learnerId: Int = 0,
    val learnerName: String = "",
    val learningMode: String = "",
    val minutePerSession: Int = 0,
    val numberOfLearner: Int = 0,
    val rate: Int = 0,
    val reviewDetail: String = "",
    val sessionPerWeek: Int = 0,
    val status: String = "",
    val subjectId: Int = 0,
    val subjectName: String = "",
    val title: String = "",
    val tutorEmail: String = "",
    val tutorId: Int = 0,
    val tutorName: String = "",
    val tutorPhoneNumber: String = ""
)
fun LearningCourseDetail.isValid() : Boolean {
    return id != -1
}