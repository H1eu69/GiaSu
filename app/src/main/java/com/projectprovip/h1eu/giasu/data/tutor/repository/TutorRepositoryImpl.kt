package com.projectprovip.h1eu.giasu.data.tutor.repository

import com.projectprovip.h1eu.giasu.data.tutor.api.TutorApi
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto.TutorDetailDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto.TutorDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRegisterDto.TutorRegisterDto
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import retrofit2.Response
import javax.inject.Inject

class TutorRepositoryImpl @Inject constructor(
    private val api: TutorApi
) : TutorRepository {
    override suspend fun getAllTutor(pageIndex: Int): TutorDto {
        return api.getAllTutor(pageIndex)
    }

    override suspend fun getTutorDetail(tutorId: String): TutorDetailDto {
        return api.getTutorDetail(tutorId)
    }

    override suspend fun registerTutor(
        auth: String,
        tutorRegisterInput: TutorRegisterInput
    ): TutorRegisterDto {
        return api.registerTutor(auth, tutorRegisterInput)
    }
}