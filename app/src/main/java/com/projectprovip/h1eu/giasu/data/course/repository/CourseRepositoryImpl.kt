package com.projectprovip.h1eu.giasu.data.course.repository

import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.dto.ListCourseInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.domain.course.repository.ClassesRepository
import retrofit2.Response
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi
) : ClassesRepository{
    override suspend fun getAllClasses(): ListCourseInformationDto {
        return api.getAllClasses()
    }

    override suspend fun registerCourse(id: Int, token: String?): Response<RequestCourseDto> {
        return api.registerCourse(id, token!!)
    }
}