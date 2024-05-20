package com.projectprovip.h1eu.giasu.domain.tutor.model

data class Tutor(
    val academicLevel: String = "Dummy academicLevel",
    val birthYear: Int = -1000,
    val description: String = "Dummy description",
    val firstName: String = "Dummy firstName",
    val id: String = "-1",
    val image: String = "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp",
    val lastName: String = "Dummy lastName",
    val rate: Double = 1.0,
    val university: String = "Dummy university"
)