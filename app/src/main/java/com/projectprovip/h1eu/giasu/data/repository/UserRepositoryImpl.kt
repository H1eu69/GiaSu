package com.projectprovip.h1eu.giasu.data.repository

import com.projectprovip.h1eu.giasu.data.remote.UserAuthApi
import com.projectprovip.h1eu.giasu.data.remote.dto.UserLoginDto
import com.projectprovip.h1eu.giasu.data.remote.dto.UserSignUpDto
import com.projectprovip.h1eu.giasu.data.remote.model.UserLoginInput
import com.projectprovip.h1eu.giasu.data.remote.model.UserSignUpInput
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