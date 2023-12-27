package com.projectprovip.h1eu.giasu.domain.tutor.model

data class Tutor(
    val academicLevel: String,
    val birthYear: Int,
    val description: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String = "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
    val isVerified: Boolean,
    val lastName: String,
    val phoneNumber: String,
    val rate: Int,
    val university: String
)