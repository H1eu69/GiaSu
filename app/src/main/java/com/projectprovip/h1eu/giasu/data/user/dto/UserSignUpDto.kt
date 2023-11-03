package com.projectprovip.h1eu.giasu.data.user.dto

data class UserSignUpDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    val value: Value
)