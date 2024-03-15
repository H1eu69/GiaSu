package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.common.isEmailFormatted
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.domain.authentication.usecase.LoginUseCase
import com.projectprovip.h1eu.giasu.presentation.authentication.model.AuthState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.LoginState
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
        if (!validate(email)) {
            _loginState.value = LoginState(
                auth = AuthState.WRONG_EMAIL_FORMAT
            )
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

    private fun validate(email: String): Boolean {
        Log.d("email", email)
        return email.isEmailFormatted()
    }
}