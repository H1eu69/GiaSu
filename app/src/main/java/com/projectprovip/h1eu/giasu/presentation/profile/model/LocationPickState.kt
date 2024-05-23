package com.projectprovip.h1eu.giasu.presentation.profile.model

import com.projectprovip.h1eu.giasu.domain.location.model.District
import com.projectprovip.h1eu.giasu.domain.location.model.Province
import com.projectprovip.h1eu.giasu.domain.location.model.Ward

data class LocationPickState(
    val isLoading: Boolean = false,
    val province: List<ProvinceItem> = emptyList(),
    val district: List<DistrictItem> = emptyList(),
    val ward: List<WardItem> = emptyList(),
    val error: String = ""
)

data class WardItem(
    val district_id: String = "dummy ward",
    val ward_id: String = "dummy ward",
    val ward_name: String = "dummy ward",
    val ward_type: String = "dummy ward",
    var isSelected: Boolean = false,
)

data class DistrictItem(
    val district_id: String = "",
    val district_name: String = "dummy district",
    val district_type: String = "dummy district",
    val province_id: String = "dummy district",
    var isSelected: Boolean = false,
)

data class ProvinceItem(
    val provinceId: String = "",
    val provinceName: String = "dummy province",
    var isSelected: Boolean = false,
)

fun Province.toProvinceItem() =
    ProvinceItem(
        provinceId, provinceName
    )

fun District.toDistrictItem() =
    DistrictItem(district_id, district_name, district_type, province_id)

fun Ward.toWardItem() =
    WardItem(
        district_id, ward_id, ward_name, ward_type
    )