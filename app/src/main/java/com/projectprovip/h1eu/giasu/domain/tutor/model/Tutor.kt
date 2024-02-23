package com.projectprovip.h1eu.giasu.domain.tutor.model

data class Tutor(
    val academicLevel: String = "Dummy academicLevel",
    val birthYear: Int = -1000,
    val description: String = "Dummy description",
    val firstName: String = "Dummy firstName",
    val gender: String = "Dummy gender",
    val id: Int = -1,
    val image: String = "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
    val isVerified: Boolean = true,
    val lastName: String = "Dummy lastName",
    val phoneNumber: String = "Dummy phoneNumber",
    val rate: Int = 1,
    val university: String = "Dummy university"
)