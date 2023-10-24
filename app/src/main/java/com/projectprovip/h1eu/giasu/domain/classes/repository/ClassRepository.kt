package com.projectprovip.h1eu.giasu.domain.classes.repository

import com.projectprovip.h1eu.giasu.data.classes.dto.ClassInformationDto

interface ClassesRepository {
    suspend fun getAllClasses(pageIndex: Int, subjectName: String?) : ClassInformationDto
}