package com.projectprovip.h1eu.giasu.data.tutor.api

import com.projectprovip.h1eu.giasu.data.tutor.dto.TutorDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TutorApi {
    @GET("TutorInformation")
    suspend fun getAllTutor(@Query("PageIndex") index: Int) : TutorDto
}