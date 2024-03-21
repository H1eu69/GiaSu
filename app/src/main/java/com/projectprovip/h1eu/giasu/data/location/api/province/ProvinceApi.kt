package com.projectprovip.h1eu.giasu.data.location.api.province

import com.projectprovip.h1eu.giasu.data.location.dto.province.ProvinceDto
import retrofit2.http.GET

interface ProvinceApi {
    @GET("/api/province/")
    suspend fun getProvince() : ProvinceDto
}