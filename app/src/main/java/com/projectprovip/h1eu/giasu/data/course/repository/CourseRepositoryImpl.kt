package com.projectprovip.h1eu.giasu.data.course.repository

import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.dto.ListCourseInformationDto
import com.projectprovip.h1eu.giasu.domain.course.repository.ClassesRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi
) : ClassesRepository{
    override suspend fun getAllClasses(): ListCourseInformationDto {
        return api.getAllClasses()
    }
}