package com.projectprovip.h1eu.giasu.data.course.api

import com.projectprovip.h1eu.giasu.data.course.dto.ListCourseInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.CourseInformationDtoItem
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CourseApi {
    @GET("Course")
    suspend fun getAllClasses() : ListCourseInformationDto

    @GET("Course/{id}")
    suspend fun getClassById(@Path("id") id: Int) : CourseInformationDtoItem

    @PUT("Course/{courseId}/RequestCourse")
    suspend fun registerCourse(@Path("courseId") id: Int,
                               @Header("Authorization") token: String) : Response<RequestCourseDto>
    @GET("Profile/GetCourseRequests")
    suspend fun getRequestedCourse(@Header("Authorization") token: String) : Response<RequestedCourseDto>
}