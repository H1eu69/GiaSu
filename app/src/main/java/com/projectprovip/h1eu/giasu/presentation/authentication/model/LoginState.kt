package com.projectprovip.h1eu.giasu.presentation.authentication.model
import com.projectprovip.h1eu.giasu.domain.authentication.model.User

data class LoginState(
    val isLoading : Boolean = false,
    val user : User? = null,
    val error: String = "",
    val token: String? = null
)