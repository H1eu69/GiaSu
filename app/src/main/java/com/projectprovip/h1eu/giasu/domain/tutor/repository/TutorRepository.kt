package com.projectprovip.h1eu.giasu.domain.tutor.repository

import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto.TutorDetailDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto.TutorDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRegisterDto.TutorRegisterDto
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import retrofit2.Response

interface TutorRepository {
    suspend fun getAllTutor(pageIndex: Int) : TutorDto
    suspend fun getTutorDetail(tutorId: String) : TutorDetailDto
    suspend fun registerTutor(auth: String, tutorRegisterInput: TutorRegisterInput) : TutorRegisterDto
}