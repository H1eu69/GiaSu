package com.projectprovip.h1eu.giasu.data.subject.api

import com.projectprovip.h1eu.giasu.data.subject.dto.SubjectDto
import retrofit2.http.GET

interface SubjectApi {
    @GET("Subject")
    suspend fun getSubject(
    ): SubjectDto


}