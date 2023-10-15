package com.projectprovip.h1eu.giasu.domain.authentication.repository

import com.projectprovip.h1eu.giasu.data.remote.UserAuthApi
import com.projectprovip.h1eu.giasu.data.remote.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.remote.model.UserLoginInput

interface UserRepository {
    suspend fun login(userLoginInput: UserLoginInput) : UserLoginDto
}