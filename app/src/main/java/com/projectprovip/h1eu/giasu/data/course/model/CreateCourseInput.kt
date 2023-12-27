package com.projectprovip.h1eu.giasu.data.course.model

data class CreateCourseInput(
    val academicLevel: String,
    val address: String,
    val chargeFee: Int,
    val contactNumber: String,
    val description: String,
    val fee: Int,
    val genderRequirement: String,
    val learnerGender: String,
    val learningMode: String,
    val minutePerSession: Int,
    val numberOfLearner: Int,
    val sessionPerWeek: Int,
    val subjectName: String,
    val title: String
)