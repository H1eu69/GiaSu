package com.projectprovip.h1eu.giasu.presentation.home.model

import com.projectprovip.h1eu.giasu.domain.course.model.CourseDetail

data class CourseDetailState(
    var isLoading: Boolean = false,
    var data: List<CourseDetail> = emptyList(),
    var error: String? = null
)

data class CourseDetail(
    val academicLevelRequirement: String,
    val address: String,
    val chargeFee: Double,
    val contactNumber: String,
    val description: String,
    val fee: Double,
    val genderRequirement: String,
    val id: Int,
    val learnerGender: String,
    val learningMode: String,
    val minutePerSession: Int,
    val numberOfLearner: Int,
    val sessionPerWeek: Int,
    val status: String,
    val subjectId: Int,
    val subjectName: String,
    val title: String,
    val creationTime: String
)