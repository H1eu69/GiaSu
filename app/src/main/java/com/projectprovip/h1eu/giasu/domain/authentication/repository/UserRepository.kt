package com.projectprovip.h1eu.giasu.domain.authentication.repository

import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.user.dto.signupDto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.domain.authentication.model.UserSignUpParams

interface UserRepository {
    suspend fun login(userLoginInput: UserLoginInput) : UserLoginDto
    suspend fun register(userSignUpInput: UserSignUpParams): UserSignUpDto
}