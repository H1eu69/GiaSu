package com.projectprovip.h1eu.giasu.data.user.repository

import com.projectprovip.h1eu.giasu.data.user.api.UserAuthApi
import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.user.dto.signupDto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserAuthApi
) : UserRepository {
    override suspend fun login(userLoginInput: UserLoginInput): UserLoginDto {
        return api.sendLoginRequest(userLoginInput)
    }

    override suspend fun register(userSignUpInput: UserSignUpInput): UserSignUpDto {
        return api.sendSignUpRequest(userSignUpInput)
    }
}