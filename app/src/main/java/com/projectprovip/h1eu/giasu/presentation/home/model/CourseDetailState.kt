package com.projectprovip.h1eu.giasu.presentation.home.model

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail

data class CourseDetailState(
    var isLoading: Boolean = false,
    var data: List<CourseDetail> = emptyList(),
    var error: String? = null
)

data class CourseDetail(
    val academicLevelRequirement: String = "dummy academicLevelRequirement",
    val address: String = "dummy academicLevelRequirement",
    val chargeFee: Double = 123.5,
    val contactNumber: String = "dummy academicLevelRequirement",
    val description: String = "dummy academicLevelRequirement",
    val fee: Double = 123.5,
    val genderRequirement: String = "dummy academicLevelRequirement",
    val id: Int = -1,
    val learnerGender: String = "dummy academicLevelRequirement",
    val learningMode: String = "dummy academicLevelRequirement",
    val minutePerSession: Int = -1,
    val numberOfLearner: Int = -1,
    val sessionPerWeek: Int = -1,
    val status: String = "dummy academicLevelRequirement",
    val subjectId: Int = -1,
    val subjectName: String = "dummy academicLevelRequirement",
    val title: String = "dummy academicLevelRequirement",
    val creationTime: String = "dummy academicLevelRequirement"
)