package com.projectprovip.h1eu.giasu.domain.profile.model

data class Profile(
    val avatar: String = "",
    val birthYear: Int = -1,
    val city: String = "",
    val country: String = "",
    val description: String = "",
    val email: String = "",
    val firstName: String = "",
    val gender: Int = 1,
    val lastName: String = "",
    val phoneNumber: String = "",
    val role: String = ""
)
