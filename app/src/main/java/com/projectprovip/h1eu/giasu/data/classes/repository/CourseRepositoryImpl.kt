package com.projectprovip.h1eu.giasu.data.classes.repository

import com.projectprovip.h1eu.giasu.data.classes.api.CourseApi
import com.projectprovip.h1eu.giasu.data.classes.dto.ListCourseInformationDto
import com.projectprovip.h1eu.giasu.domain.course.repository.ClassesRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi
) : ClassesRepository{
    override suspend fun getAllClasses(): ListCourseInformationDto {
        return api.getAllClasses()
    }
}