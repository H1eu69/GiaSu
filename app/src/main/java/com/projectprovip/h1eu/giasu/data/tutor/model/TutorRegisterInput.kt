package com.projectprovip.h1eu.giasu.data.tutor.model

data class TutorRegisterInput(
    val academicLevel: String,
    val university: String,
    val majors: List<Int>,
    val verificationImage: List<String>,
    val description: String = "",
)