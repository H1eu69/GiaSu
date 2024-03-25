package com.projectprovip.h1eu.giasu.data.location.dto.ward

import com.projectprovip.h1eu.giasu.domain.location.model.Ward

data class Result(
    val district_id: String,
    val ward_id: String,
    val ward_name: String,
    val ward_type: String
)

fun Result.toWard() = Ward(
    district_id, ward_id, ward_name, ward_type
)