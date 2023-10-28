package com.projectprovip.h1eu.giasu.domain.classes.model

data class NewClassDetail(
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
    val title: String
)