package com.projectprovip.h1eu.giasu.domain.tutor.model

import com.projectprovip.h1eu.giasu.data.tutor.dto.ReviewDetailDto

data class TutorDetail(
    val academicLevel: String = "dummy data",
    val address: String = "dummy data",
    val birthYear: Int = -1,
    val description: String = "dummy data",
    val email: String = "dummy data",
    val firstName: String = "dummy data",
    val gender: String = "dummy data",
    val id: Int = -1,
    val image: String = "dummy data",
    val isVerified: Boolean = false,
    val lastName: String = "dummy data",
    val phoneNumber: String = "dummy data",
    val rate: Int = -1,
    val reviewDetailDtos: List<ReviewDetailDto> = emptyList(),
    val subjects: List<String> = emptyList(),
    val university: String  = "dummy data"
)