package com.projectprovip.h1eu.giasu.domain.authentication.usecase

import com.projectprovip.h1eu.giasu.domain.authentication.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
}