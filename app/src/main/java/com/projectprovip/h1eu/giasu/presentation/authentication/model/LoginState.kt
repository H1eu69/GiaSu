package com.projectprovip.h1eu.giasu.presentation.authentication.model

import com.projectprovip.h1eu.giasu.data.user.dto.loginDto.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = "",
    val token: String? = null,
    val validation: Validation = Validation.IDLE
)

enum class Validation(val text: String?) {
    IDLE(text = null),
    WRONG_EMAIL_FORMAT("Wrong email format"),
    PASSWORD("Password must be at least 8 characters long and contain at least one lowercase letter (a-z), one uppercase letter (A-Z), one digit (0-9), and one special character (!,@,#,$,%,^,&,*)"),
}