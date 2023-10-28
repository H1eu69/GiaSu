package com.projectprovip.h1eu.giasu.domain.classes.repository

import com.projectprovip.h1eu.giasu.data.classes.dto.CourseInformationDto

interface ClassesRepository {
    suspend fun getAllClasses() : CourseInformationDto
}