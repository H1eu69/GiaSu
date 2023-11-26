package com.projectprovip.h1eu.giasu.domain.tutor.repository

import com.projectprovip.h1eu.giasu.data.tutor.dto.TutorDto

interface TutorRepository {
    suspend fun getAllTutor() : TutorDto
}