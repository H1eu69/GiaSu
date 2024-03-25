package com.projectprovip.h1eu.giasu.data.location.dto.district

import com.projectprovip.h1eu.giasu.domain.location.model.District

data class Result(
    val district_id: String,
    val district_name: String,
    val district_type: String,
    val lat: Any,
    val lng: Any,
    val province_id: String
)

fun Result.toDistrict() = District(
    district_id, district_name, district_type, province_id
)