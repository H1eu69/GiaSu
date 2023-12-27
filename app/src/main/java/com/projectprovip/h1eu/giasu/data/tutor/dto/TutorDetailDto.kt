package com.projectprovip.h1eu.giasu.data.tutor.dto

import com.google.gson.annotations.SerializedName

data class TutorDetailDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    @SerializedName("value")
    val tutorDetail: TutorDetail
)