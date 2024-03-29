package com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto

import com.projectprovip.h1eu.giasu.domain.profile.model.TutorInfo

data class Value(
    val academicLevel: String,
    val creationTime: String,
    val id: String,
    val isVerified: Boolean,
    val lastModificationTime: String,
    val majors: List<Major>,
    val rate: Int,
    val university: String,
    val verificationDtos: List<Any>
)

fun Value.toTutorInfo() = TutorInfo(
    academicLevel, id, isVerified, majors, rate, university, verificationDtos
)