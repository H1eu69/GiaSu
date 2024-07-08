package com.projectprovip.h1eu.giasu.presentation.authentication.model

import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.User
import com.projectprovip.h1eu.giasu.domain.location.model.Province

data class ProvinceState(
    val isLoading: Boolean = false,
    val province: List<ProvinceItem> = listOf(
        ProvinceItem()
    ),
    val error: String = "",
)

data class ProvinceItem (
    val provinceId: String = "",
    val provinceName: String = "dummy province",
    var isSelected: Boolean = false
)

fun Province.toProvinceItem() = ProvinceItem(
    provinceId, provinceName
)