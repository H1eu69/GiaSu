package com.projectprovip.h1eu.giasu.domain.location.repository

import com.projectprovip.h1eu.giasu.data.location.dto.province.ProvinceDto

interface ProvinceRepository {
    suspend fun getProvince() : ProvinceDto
}