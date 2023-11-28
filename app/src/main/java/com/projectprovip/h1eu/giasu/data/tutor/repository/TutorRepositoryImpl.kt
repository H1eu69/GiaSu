package com.projectprovip.h1eu.giasu.data.tutor.repository

import com.projectprovip.h1eu.giasu.data.tutor.api.TutorApi
import com.projectprovip.h1eu.giasu.data.tutor.dto.TutorDto
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import javax.inject.Inject

class TutorRepositoryImpl @Inject constructor(
    private val api: TutorApi
) : TutorRepository {
    override suspend fun getAllTutor(pageIndex: Int): TutorDto {
        return api.getAllTutor(pageIndex)
    }
}