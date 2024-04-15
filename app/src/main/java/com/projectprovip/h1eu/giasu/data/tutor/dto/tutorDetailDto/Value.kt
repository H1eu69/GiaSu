package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto

import com.projectprovip.h1eu.giasu.domain.tutor.model.TutorDetail


data class Value(
    val academicLevel: String,
    val address: String,
    val avatar: String,
    val birthYear: Int,
    val creationTime: String,
    val description: String,
    val fullName: String,
    val gender: String,
    val id: String,
    val lastModificationTime: String,
    val rate: Int,
    val reviews: List<ReviewDetailDto>,
    val tutorMajors: List<String>,
    val university: String
)

fun Value.toTutorDetail() = TutorDetail(
    academicLevel,
    address,
    avatar,
    birthYear,
    creationTime,
    description,
    fullName,
    gender,
    id,
    lastModificationTime,
    rate,
    reviews,
    tutorMajors,
    university
)