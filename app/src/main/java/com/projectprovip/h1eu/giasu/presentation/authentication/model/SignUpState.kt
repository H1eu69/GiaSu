package com.projectprovip.h1eu.giasu.presentation.authentication.model

import com.projectprovip.h1eu.giasu.domain.authentication.model.User

data class SignUpState (
    var isLoading: Boolean = false,
    var user: User? = null,
    var error: String = "",
    var token: String? = null
)