package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto

import com.projectprovip.h1eu.giasu.presentation.common.keepFirstDecimalDouble
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor

data class Item(
    val academicLevel: String,
    val avatar: String,
    val birthYear: Int,
    val description: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val rate: Double,
    val university: String
)

fun Item.toTutor() = Tutor(
    academicLevel, birthYear, description,
    firstName,
    id, avatar, lastName, rate.keepFirstDecimalDouble(), university
)