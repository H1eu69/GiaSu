package com.projectprovip.h1eu.giasu.data.user.api

import com.projectprovip.h1eu.giasu.data.user.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.user.dto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthApi {
    @POST("Authentication/Login")
    suspend fun sendLoginRequest(@Body userLoginInput: UserLoginInput) : UserLoginDto

    @POST("Authentication/Register")
    suspend fun sendSignUpRequest(@Body userSignUpInput: UserSignUpInput) : UserSignUpDto
}