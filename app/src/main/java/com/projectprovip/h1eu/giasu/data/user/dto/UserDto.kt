package com.projectprovip.h1eu.giasu.data.user.dto

import com.projectprovip.h1eu.giasu.domain.authentication.model.User

data class UserDto(
    val email: String,
    val fullName: String,
    val image: String,
    val role: Int
)

fun UserDto.toUser() = User(
    email = email,
    fullName = fullName,
    image = image
)