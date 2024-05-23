package com.projectprovip.h1eu.giasu.domain.profile.model

import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.Major
import com.projectprovip.h1eu.giasu.data.profile.dto.tutorInfoDto.VerificationDto

data class TutorInfo(
    val academicLevel: String = "",
    val id: String = "",
    val isVerified: Boolean = false,
    val majors: List<Major> = emptyList(),
    val rate: Int = -1,
    val university: String = "",
    val verificationDtos: List<VerificationDto> = emptyList()
)