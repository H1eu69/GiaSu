package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.presentation.common.isEmailFormatted
import com.projectprovip.h1eu.giasu.presentation.common.isPasswordFormatted
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.domain.authentication.usecase.LoginUseCase
import com.projectprovip.h1eu.giasu.presentation.authentication.model.LoginState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun loginByEmail(email: String, password: String) {
        if (!validate(email, password)) {
            return
        } else
            loginUseCase(UserLoginInput(email, password)).onEach { result ->
                when (result) {
                    is EDSResult.Success -> {
                        _loginState.value = LoginState(
                            user = result.data!!.user,
                            token = result.data.token
                        )
                    }

                    is EDSResult.Error -> {
                        _loginState.value = LoginState(
                            error = result.message ?: "Unexpected error",
                        )
                    }

                    is EDSResult.Loading -> {
                        _loginState.value = LoginState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun validate(email: String, password: String): Boolean {
        var flag = true
        if (!email.isEmailFormatted()) {
            _loginState.value = LoginState(
                validation = Validation.WRONG_EMAIL_FORMAT
            )
            flag = false
        } else if (!password.isPasswordFormatted()) {
            _loginState.value = LoginState(
                validation = Validation.PASSWORD
            )
            flag = false
        }
        return flag
    }
}