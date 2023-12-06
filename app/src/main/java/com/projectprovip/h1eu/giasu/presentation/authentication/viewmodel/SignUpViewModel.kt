package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.domain.authentication.usecase.SignUpUseCase
import com.projectprovip.h1eu.giasu.presentation.authentication.model.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private var _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState

    fun signUp(firstName:String,
               lastName:String,
               email: String,
               pwd: String ) {
        signUpUseCase(
            UserSignUpInput(
                email = email,
                firstName = firstName,
                lastName = lastName,
                password = pwd,
            )
        ).onEach { result ->
            when(result) {
                is EDSResult.Loading -> {
                    _signUpState.value = SignUpState(isLoading = true)
                }
                is EDSResult.Error -> {
                    Log.e("SignUpViewModel", result.message ?: "Unexpected error")
                    _signUpState.value = SignUpState(error = result.message ?: "Unexpected error")
                }
                is EDSResult.Success -> {
                    _signUpState.value = SignUpState(user = result.data!!.userWithToken.user,
                        token = result.data.userWithToken.token)
                }
            }
        }.launchIn(viewModelScope)
    }
}