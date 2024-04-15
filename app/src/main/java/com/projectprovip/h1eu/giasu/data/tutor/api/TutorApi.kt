package com.projectprovip.h1eu.giasu.data.tutor.api

import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto.TutorDetailDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto.TutorDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRegisterDto.TutorRegisterDto
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TutorApi {
    @GET("tutor")
    suspend fun getAllTutor(@Query("PageIndex") index: Int) : TutorDto
    @GET("tutor/{id}")
    suspend fun getTutorDetail(@Path("id") tutorId: String) : TutorDetailDto
    @POST("tutor/register")
    suspend fun registerTutor(@Header("Authorization") auth: String,
                              @Body tutorRegisterInput: TutorRegisterInput) : TutorRegisterDto

}