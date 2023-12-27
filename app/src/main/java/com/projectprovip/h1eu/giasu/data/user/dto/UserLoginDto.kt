package com.projectprovip.h1eu.giasu.data.user.dto

import com.google.gson.annotations.SerializedName

data class UserLoginDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    @SerializedName(value = "value")
    val userWithToken: UserWithToken
)