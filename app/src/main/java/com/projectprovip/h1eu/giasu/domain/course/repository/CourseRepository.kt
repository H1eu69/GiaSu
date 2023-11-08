package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.ListCourseInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import retrofit2.Response

interface ClassesRepository {
    suspend fun getAllClasses() : ListCourseInformationDto
    suspend fun registerCourse(id: Int, token: String?) : Response<RequestCourseDto>
}