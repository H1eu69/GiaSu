package com.projectprovip.h1eu.giasu.data.tutor.api

import com.projectprovip.h1eu.giasu.data.tutor.dto.TutorDto
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TutorApi {
    @GET("TutorInformation")
    suspend fun getAllTutor(@Query("PageIndex") index: Int) : TutorDto

    @POST("TutorInformation/Register")
    suspend fun registerTutor(@Header("Authorization") auth: String,
                              @Body tutorRegisterInput: TutorRegisterInput) : Response<Unit>

}