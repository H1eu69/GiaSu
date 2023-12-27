package com.projectprovip.h1eu.giasu.data.tutor.dto

import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor

data class TutorInformation(
    val academicLevel: String,
    val birthYear: Int,
    val creationTime: String,
    val description: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val isVerified: Boolean,
    val lastModificationTime: String,
    val lastName: String,
    val phoneNumber: String,
    val rate: Int,
    val university: String
)

fun TutorInformation.toTutor() = Tutor(
    academicLevel, birthYear, description, firstName, gender, id, image, isVerified, lastName, phoneNumber, rate, university
)