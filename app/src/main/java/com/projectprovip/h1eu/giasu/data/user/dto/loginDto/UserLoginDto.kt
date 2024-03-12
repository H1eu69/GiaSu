package com.projectprovip.h1eu.giasu.data.user.dto.loginDto

import com.google.gson.annotations.SerializedName

data class UserLoginDto(
    val displayMessage: String,
    val error: Error,
    val errors: List<Any>,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    @SerializedName(value = "value")
    val value: UserToken?
)