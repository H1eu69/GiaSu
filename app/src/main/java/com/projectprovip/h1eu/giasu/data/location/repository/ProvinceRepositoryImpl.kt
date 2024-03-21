package com.projectprovip.h1eu.giasu.data.location.repository

import com.projectprovip.h1eu.giasu.data.location.api.province.ProvinceApi
import com.projectprovip.h1eu.giasu.data.location.dto.province.ProvinceDto
import com.projectprovip.h1eu.giasu.domain.location.repository.ProvinceRepository
import javax.inject.Inject

class ProvinceRepositoryImpl @Inject constructor(
    private val api: ProvinceApi
) : ProvinceRepository{
    override suspend fun getProvince(): ProvinceDto {
        return api.getProvince()
    }
}