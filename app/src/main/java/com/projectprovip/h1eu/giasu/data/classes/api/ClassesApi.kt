package com.projectprovip.h1eu.giasu.data.classes.api

import com.projectprovip.h1eu.giasu.data.classes.dto.CourseInformationDto
import com.projectprovip.h1eu.giasu.data.classes.dto.CourseInformationDtoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ClassesApi {
    @GET("Course")
    suspend fun getAllClasses() : CourseInformationDto


    @GET("Course/{id}")
    suspend fun getClassById(@Path("id") id: Int) : CourseInformationDtoItem
}