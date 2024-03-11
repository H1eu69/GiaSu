package com.projectprovip.h1eu.giasu.domain.tutor.model

import com.projectprovip.h1eu.giasu.data.tutor.dto.ReviewDetailDto

data class TutorDetail(
    val academicLevel: String = "dummy Lecturer",
    val address: String = "dummy data",
    val birthYear: Int = -1,
    val description: String = "dummy description",
    val email: String = "dummyemail@gmail.com",
    val firstName: String = "dummy firstName",
    val gender: String = "dummy gender",
    val id: Int = -1,
    val image: String = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Chris_Hemsworth_by_Gage_Skidmore_2_%28cropped%29.jpg",
    val isVerified: Boolean = false,
    val lastName: String = "dummy lastName",
    val phoneNumber: String = "0967075340",
    val rate: Int = -1,
    val reviewDetailDtos: List<ReviewDetailDto> = emptyList(),
    val subjects: List<String> = listOf(
        "Dummy Java",
        "Dummy",
        "Dummy Toan Cao Cap",
        "Dummy Guitar",
        "Dummy English",
        "Dummy Lap trinh",
        "Dummy C++",
    ),
    val university: String = "dummy university"
)