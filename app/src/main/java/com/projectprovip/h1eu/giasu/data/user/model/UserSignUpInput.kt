package com.projectprovip.h1eu.giasu.data.user.model

data class UserSignUpInput(
    val address: String,
    val birthYear: Int,
    val email: String,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String
)