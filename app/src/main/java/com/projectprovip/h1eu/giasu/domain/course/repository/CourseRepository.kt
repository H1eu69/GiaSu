package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.classes.dto.ListCourseInformationDto

interface ClassesRepository {
    suspend fun getAllClasses() : ListCourseInformationDto
}