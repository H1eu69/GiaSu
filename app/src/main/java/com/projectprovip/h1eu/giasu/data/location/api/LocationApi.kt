package com.projectprovip.h1eu.giasu.data.location.api

import com.projectprovip.h1eu.giasu.data.location.dto.district.DistrictDto
import com.projectprovip.h1eu.giasu.data.location.dto.province.ProvinceDto
import com.projectprovip.h1eu.giasu.data.location.dto.ward.WardDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApi {
    @GET("/api/province/")
    suspend fun getProvince() : ProvinceDto

    @GET("/api/province/district/{province_id}")
    suspend fun getDistrict(
        @Path("province_id") provinceId: String
    ) : DistrictDto

    @GET("/api/province/ward/{district_id}")
    suspend fun getWard(
        @Path("district_id") districtId: String
    ) : WardDto
}