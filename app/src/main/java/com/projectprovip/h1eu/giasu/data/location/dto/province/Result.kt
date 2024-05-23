package com.projectprovip.h1eu.giasu.data.location.dto.province

import com.projectprovip.h1eu.giasu.domain.location.model.Province

data class Result(
    val province_id: String,
    val province_name: String,
    val province_type: String
)

fun Result.toProvince() = Province(
    province_id, province_name
)