package com.projectprovip.h1eu.giasu.data.tutor.dto

data class ReviewDetailDto(
    val courseId: Int,
    val detail: String,
    val learnerId: Int,
    val learnerName: String,
    val rate: Int
)