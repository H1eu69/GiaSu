package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.user.dto.toUser
import com.projectprovip.h1eu.giasu.data.user.model.UserLoginInput
import com.projectprovip.h1eu.giasu.domain.authentication.usecase.LoginUseCase
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
        loginUseCase(UserLoginInput(email, password)).onEach { result ->
            when(result) {
                is Result.Success -> {
                    Log.d("AuthViewModel", "Login success")
                    _loginState.value = LoginState(
                        user = result.data!!.user.toUser(),
                        token = result.data.token
                    )
                }
                is Result.Error -> {
                    Log.e("AuthViewModel", result.message.toString())
                    _loginState.value = LoginState(error = result.message ?: "Unexpected error")
                }
                is Result.Loading -> {
                    Log.d("AuthViewModel", "Login loading")
                    _loginState.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}