package com.projectprovip.h1eu.giasu.domain.location.repository

import com.projectprovip.h1eu.giasu.data.location.dto.district.DistrictDto
import com.projectprovip.h1eu.giasu.data.location.dto.province.ProvinceDto
import com.projectprovip.h1eu.giasu.data.location.dto.ward.WardDto

interface LocationRepository {
    suspend fun getProvince() : ProvinceDto
    suspend fun getDistrict(provinceId: String) : DistrictDto
    suspend fun getWard(districtId: String) : WardDto
}