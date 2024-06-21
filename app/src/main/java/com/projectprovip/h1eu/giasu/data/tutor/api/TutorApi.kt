package com.projectprovip.h1eu.giasu.data.tutor.api

import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDetailDto.TutorDetailDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto.TutorDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRecDto.TutorRecDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRegisterDto.TutorRegisterDto
import com.projectprovip.h1eu.giasu.data.tutor.dto.tutorRequestDto.TutorRequestDto
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
    suspend fun getAllTutor(
        @Query("PageIndex") index: Int,
        @Query("Subject") subject: String?
    ): TutorDto

    @GET("tutor/{id}")
    suspend fun getTutorDetail(@Path("id") tutorId: String): TutorDetailDto

    @POST("tutor/register")
    suspend fun registerTutor(
        @Header("Authorization") auth: String,
        @Body tutorRegisterInput: TutorRegisterInput
    ): TutorRegisterDto

    @POST("tutor/{id}/request-tutor")
    suspend fun requestTutor(
        @Header("Authorization") auth: String, @Path("id") tutorId: String,
        @Body tutorRequestBody: TutorRequestBody
    ): TutorRequestDto

    @POST("tutor/by-ids")
    suspend fun getRecTutor(
        @Body body: List<String>
    ): TutorRecDto
}

data class TutorRequestBody(
    val requestMessage: String
)