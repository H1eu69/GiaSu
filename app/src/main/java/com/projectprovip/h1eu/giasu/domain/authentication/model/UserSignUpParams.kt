package com.projectprovip.h1eu.giasu.domain.authentication.model

data class UserSignUpParams(
    val birthYear: Int,
    val city: String,
    val country: String = "Vietnam",
    val email: String,
    val firstname: String,
    val gender: Int = 2,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val username: String
)