package com.projectprovip.h1eu.giasu.domain.tutor.model

import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto.ReviewDetailDto

data class TutorDetail(
    val academicLevel: String = "dummy",
    val address: String = "dummy",
    val avatar: String = "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
    val birthYear: Int = 2000,
    val creationTime: String = "2014-12-23T17:13:36",
    val description: String = "dummy",
    val fullName: String = "dummy",
    val gender: String = "dummy",
    val id: String = "dummy",
    val lastModificationTime: String = "2014-12-23T17:13:36",
    val rate: Double = 1.0,
    val reviews: List<ReviewDetailDto> = emptyList(),
    val tutorMajors: List<String> = emptyList(),
    val university: String = "dummy"
)