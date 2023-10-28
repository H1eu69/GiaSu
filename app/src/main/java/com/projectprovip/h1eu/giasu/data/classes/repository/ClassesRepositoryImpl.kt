package com.projectprovip.h1eu.giasu.data.classes.repository

import com.projectprovip.h1eu.giasu.data.classes.api.ClassesApi
import com.projectprovip.h1eu.giasu.data.classes.dto.CourseInformationDto
import com.projectprovip.h1eu.giasu.domain.classes.repository.ClassesRepository
import javax.inject.Inject

class ClassesRepositoryImpl @Inject constructor(
    private val api: ClassesApi
) : ClassesRepository{
    override suspend fun getAllClasses(): CourseInformationDto {
        return api.getAllClasses()
    }
}