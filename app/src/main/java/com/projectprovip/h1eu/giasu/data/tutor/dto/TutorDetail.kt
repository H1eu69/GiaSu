package com.projectprovip.h1eu.giasu.data.tutor.dto

data class TutorDetail(
    val academicLevel: String,
    val address: String,
    val birthYear: Int,
    val creationTime: String,
    val description: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val isEmailConfirmed: Boolean,
    val isVerified: Boolean,
    val lastModificationTime: String,
    val lastName: String,
    val phoneNumber: String,
    val rate: Int,
    val reviewDetailDtos: List<ReviewDetailDto>,
    val role: String,
    val subjects: List<String>,
    val tutorVerificationInfoDtos: List<Any>,
    val university: String
)

fun TutorDetail.toTutorDetail() = com.projectprovip.h1eu.giasu.domain.tutor.model.TutorDetail(
    academicLevel,
    address,
    birthYear,
    description,
    email,
    firstName,
    gender,
    id,
    image,
    isVerified,
    lastName,
    phoneNumber,
    rate,
    reviewDetailDtos,
    subjects,
    university
)