package com.projectprovip.h1eu.giasu.data.subject.repository

import com.projectprovip.h1eu.giasu.data.subject.api.SubjectApi
import com.projectprovip.h1eu.giasu.data.subject.dto.SubjectDto
import com.projectprovip.h1eu.giasu.domain.subject.repository.SubjectRepository
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectApi: SubjectApi
) : SubjectRepository {
    override suspend fun getSubject(): SubjectDto {
        return subjectApi.getSubject()
    }
}