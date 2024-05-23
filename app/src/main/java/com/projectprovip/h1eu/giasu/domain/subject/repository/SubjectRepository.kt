package com.projectprovip.h1eu.giasu.domain.subject.repository

import com.projectprovip.h1eu.giasu.data.subject.dto.SubjectDto

interface SubjectRepository {
    suspend fun getSubject() : SubjectDto
}