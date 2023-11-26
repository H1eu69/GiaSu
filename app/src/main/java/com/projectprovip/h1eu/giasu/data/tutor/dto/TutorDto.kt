package com.projectprovip.h1eu.giasu.data.tutor.dto

import com.google.gson.annotations.SerializedName

data class TutorDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isSuccess: Boolean,
    val pageIndex: Int,
    val totalItems: Int,
    val totalPages: Int,
    @SerializedName("value")
    val tutorInformation: List<TutorInformation>
)