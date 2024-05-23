package com.projectprovip.h1eu.giasu.data.tutor.repository

import com.projectprovip.h1eu.giasu.data.tutor.api.TutorApi
import com.projectprovip.h1eu.giasu.data.tutor.api.TutorRequestBody
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto.TutorDetailDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto.TutorDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRegisterDto.TutorRegisterDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRequestDto.TutorRequestDto
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.RequestTutorParams
import retrofit2.Response
import javax.inject.Inject

class TutorRepositoryImpl @Inject constructor(
    private val api: TutorApi
) : TutorRepository {
    override suspend fun getAllTutor(pageIndex: Int, subject: String?): TutorDto {
        return api.getAllTutor(pageIndex, subject)
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

    override suspend fun requestTutor(
        auth: String,
        requestTutorParams: RequestTutorParams
    ): TutorRequestDto {
        return api.requestTutor(
            auth,
            requestTutorParams.tutorId,
            TutorRequestBody(requestTutorParams.requestMessage)
        )
    }
}