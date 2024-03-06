package com.projectprovip.h1eu.giasu.data.user.dto.signupDto

import com.google.gson.annotations.SerializedName

data class UserSignUpDto(
    val displayMessage: String,
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    @SerializedName(value = "value")
    val userToken: UserToken
)