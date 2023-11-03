package com.projectprovip.h1eu.giasu.domain.authentication.repository

import com.projectprovip.h1eu.giasu.data.user.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.user.dto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput

interface UserRepository {
    suspend fun login(userLoginInput: UserLoginInput) : UserLoginDto
    suspend fun register(userSignUpInput: UserSignUpInput): UserSignUpDto
}