package com.projectprovip.h1eu.giasu.data.location.repository

import com.projectprovip.h1eu.giasu.data.location.api.LocationApi
import com.projectprovip.h1eu.giasu.data.location.dto.district.DistrictDto
import com.projectprovip.h1eu.giasu.data.location.dto.province.ProvinceDto
import com.projectprovip.h1eu.giasu.data.location.dto.ward.WardDto
import com.projectprovip.h1eu.giasu.domain.location.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApi
) : LocationRepository {
    override suspend fun getProvince(): ProvinceDto {
        return api.getProvince()
    }

    override suspend fun getDistrict(provinceId: String): DistrictDto {
        return api.getDistrict(provinceId)
    }

    override suspend fun getWard(districtId: String): WardDto {
        return api.getWard(districtId)
    }
}