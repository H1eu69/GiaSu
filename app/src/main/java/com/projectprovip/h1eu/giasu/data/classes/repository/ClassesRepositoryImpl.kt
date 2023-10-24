package com.projectprovip.h1eu.giasu.data.classes.repository

import com.projectprovip.h1eu.giasu.data.classes.api.ClassesApi
import com.projectprovip.h1eu.giasu.data.classes.dto.ClassInformationDto
import com.projectprovip.h1eu.giasu.domain.classes.repository.ClassesRepository
import javax.inject.Inject

class ClassesRepositoryImpl @Inject constructor(
    private val api: ClassesApi
) : ClassesRepository{
    override suspend fun getAllClasses(pageIndex: Int, subjectName: String?): ClassInformationDto {
        return api.getAllClasses(pageIndex, subjectName)
    }
}