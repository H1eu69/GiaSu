package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Resource
import com.projectprovip.h1eu.giasu.data.remote.dto.toUser
import com.projectprovip.h1eu.giasu.data.remote.model.UserSignUpInput
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
                address = "Default address",
                birthYear = 45,
                email = email,
                firstName = firstName,
                lastName = lastName,
                password = pwd,
                phoneNumber = "0123456789",
                gender = "male"
            )
        ).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _signUpState.value = SignUpState(isLoading = true)
                }
                is Resource.Error -> {
                    Log.e("SignUpViewModel", result.message ?: "Unexpected error")
                    _signUpState.value = SignUpState(error = result.message ?: "Unexpected error")
                }
                is Resource.Success -> {
                    _signUpState.value = SignUpState(user = result.data!!.user.toUser())
                }
            }
        }.launchIn(viewModelScope)
    }
}