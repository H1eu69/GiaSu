package com.projectprovip.h1eu.giasu.data.user.dto

import com.projectprovip.h1eu.giasu.domain.authentication.model.User

data class UserDto(
    val email: String,
    val fullName: String,
    val id: Int,
    val image: String,
    val role: String
)

fun UserDto.toUser() = User(
    id = id,
    email = email,
    fullName = fullName,
    image = image
)