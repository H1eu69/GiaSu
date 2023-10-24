package com.projectprovip.h1eu.giasu.data.classes.api

import com.projectprovip.h1eu.giasu.data.classes.dto.ClassInformationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassesApi {
    @GET("ClassInformation/GetAllClassInformations")
    suspend fun getAllClasses(@Query("pageIndex") pageIndex: Int,
                              @Query("subjectName") subjectName: String?) : ClassInformationDto


    @GET
    suspend fun getClassById()
}