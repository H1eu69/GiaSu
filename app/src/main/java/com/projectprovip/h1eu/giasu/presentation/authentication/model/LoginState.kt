package com.projectprovip.h1eu.giasu.presentation.authentication.model

import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = "",
    val token: String? = null,
    val auth: AuthState = AuthState.IDLE
)

enum class AuthState(val text: String?) {
    IDLE(text = null), ACCOUNT_NOT_FOUND(text = "User not exist"), WRONG_EMAIL_FORMAT("Wrong email format")
}