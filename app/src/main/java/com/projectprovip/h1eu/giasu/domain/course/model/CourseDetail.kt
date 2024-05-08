package com.projectprovip.h1eu.giasu.domain.course.model

data class CourseDetail(
    val academicLevelRequirement: String = "Lecturer",
    val address: String = "35 Duong B KP1 phuong 3",
    val chargeFee: Double = -50.0,
    val creationTime: String = "2024-03-19T00:43:19.7047926-05:00",
    val description: String = "Can tim gia su chuyen day cho hoc sinh mat goc Toan, Ly, Hoa, Van, Sinh, Su, Dia. Dac biet co day cho sinh vien dai hoc cac mon dai cuong co ban: Toan cao cap, Ly, hoa,...",
    val fee: Double = -10.0,
    val genderRequirement: String = "Male",
    val id: String = "-1",
    val learnerGender: String = "Male",
    val learningMode: String = "Offline",
    val sessionDuration: Int = -90,
    val numberOfLearner: Int = -2,
    val sessionPerWeek: Int = -3,
    val status: String = "Available",
    val subjectId: Int = -1,
    val subjectName: String = "Dummy subjectName",
    val title: String = "Toan ly hoa dai cuong can ban",
    val contactNumber: String = "-0967075340",
    )