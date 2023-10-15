package com.projectprovip.h1eu.giasu.data.remote

import com.projectprovip.h1eu.giasu.data.remote.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.remote.model.UserLoginInput
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthApi {
    @POST("Auth/Login")
    suspend fun sendLoginRequest(@Body userLoginInput: UserLoginInput) : UserLoginDto
}