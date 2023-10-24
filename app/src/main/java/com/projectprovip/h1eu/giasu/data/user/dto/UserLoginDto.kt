package com.projectprovip.h1eu.giasu.data.user.dto

data class UserLoginDto(
    val isSuccess: Boolean,
    val message: String,
    val token: String,
    var user: UserDto
)