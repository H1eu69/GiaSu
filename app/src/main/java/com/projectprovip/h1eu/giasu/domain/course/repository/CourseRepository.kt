package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.ListCourseInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import retrofit2.Response

interface CoursesRepository {
    suspend fun getAllClasses(): ListCourseInformationDto
    suspend fun registerCourse(id: Int, token: String?): Response<RequestCourseDto>
    suspend fun getRequestedCourse(token: String?): Response<RequestedCourseDto>
}