package com.projectprovip.h1eu.giasu.data.user.dto

data class UserSignUpDto(
    val isSuccess: Boolean,
    val message: String,
    val token: String,
    val user: UserDto
)