package com.projectprovip.h1eu.giasu.domain.tutor.model

data class Tutor(
    val academicLevel: String,
    val birthYear: Int,
    val description: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val isVerified: Boolean,
    val lastName: String,
    val phoneNumber: String,
    val rate: Int,
    val university: String
)