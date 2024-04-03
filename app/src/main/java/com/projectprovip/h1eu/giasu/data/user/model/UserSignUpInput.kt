package com.projectprovip.h1eu.giasu.data.user.model

data class UserSignUpInput(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val userName: String,
    val phone: String,
    val birthYear: String,
    val city: String,
    val country: String = "Vietnam",
    val gender: Int = 1,

    )