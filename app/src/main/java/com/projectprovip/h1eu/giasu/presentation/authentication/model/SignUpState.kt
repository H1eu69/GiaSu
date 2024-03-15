package com.projectprovip.h1eu.giasu.presentation.authentication.model

import com.projectprovip.h1eu.giasu.data.user.dto.signupDto.User


data class SignUpState (
    var isLoading: Boolean = false,
    var user: User? = null,
    var error: String = "",
    var token: String? = null,
    var validate: Validate = Validate.IDLE
)

enum class Validate(val error: String?) {
    IDLE(null),
    FIRST_NAME_LENGTH("First name must be at least 3 characters long"),
    LAST_NAME_LENGTH("Last name must be at least 3 characters long"),
    EMAIL_FORMAT("Wrong email format"),
    PASSWORD("Password must be at least 8 characters long and contain at least one lowercase letter (a-z), one uppercase letter (A-Z), one digit (0-9), and one special character (!,@,#,$,%,^,&,*)"),
    PHONE("Phone must be at least 10 characters and only contains digits"),
    USERNAME("Username must be between 3 and 50 characters long, and can only contain letters, numbers, and underscores.")
}