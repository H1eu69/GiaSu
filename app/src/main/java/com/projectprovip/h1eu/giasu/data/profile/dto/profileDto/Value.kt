package com.projectprovip.h1eu.giasu.data.profile.dto.profileDto

import com.projectprovip.h1eu.giasu.domain.profile.model.Profile

data class Value(
    val avatar: String,
    val birthYear: Int,
    val city: String,
    val country: String,
    val creationTime: String,
    val description: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: String,
    val lastModificationTime: String,
    val lastName: String,
    val phoneNumber: String,
    val role: String
)

fun Value.toProfile() = Profile(
    avatar, birthYear, city, country, description, email, firstName, gender, lastName, phoneNumber, role
)