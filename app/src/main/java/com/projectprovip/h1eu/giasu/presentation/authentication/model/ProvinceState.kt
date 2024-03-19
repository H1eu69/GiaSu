package com.projectprovip.h1eu.giasu.presentation.authentication.model

import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.User
import com.projectprovip.h1eu.giasu.domain.location.model.Province

data class ProvinceState(
    val isLoading: Boolean = false,
    val province: List<ProvinceItem> = emptyList(),
    val error: String = "",
)

data class ProvinceItem (
    val provinceId: String,
    val provinceName: String,
    var isSelected: Boolean = false
)