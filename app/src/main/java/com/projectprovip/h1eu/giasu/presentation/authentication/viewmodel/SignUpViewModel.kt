package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.common.isEmailFormatted
import com.projectprovip.h1eu.giasu.common.isPasswordFormatted
import com.projectprovip.h1eu.giasu.common.isPhoneNumber
import com.projectprovip.h1eu.giasu.common.isUsername
import com.projectprovip.h1eu.giasu.data.user.model.UserSignUpInput
import com.projectprovip.h1eu.giasu.domain.authentication.usecase.SignUpUseCase
import com.projectprovip.h1eu.giasu.presentation.authentication.model.SignUpState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.Validate
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

    fun signUp(input: UserSignUpInput) {
        signUpUseCase(input).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _signUpState.value = SignUpState(isLoading = true)
                }

                is EDSResult.Error -> {
                    Log.e("SignUpViewModel", result.message ?: "Unexpected error")
                    _signUpState.value = SignUpState(error = result.message ?: "Unexpected error")
                }

                is EDSResult.Success -> {
                    _signUpState.value = SignUpState(
                        user = result.data!!.user,
                        token = result.data.token
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validate(
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        password: String? = null,
        username: String? = null,
        phone: String? = null,

        ): Boolean {
        var flag = true
        Log.d("Before validate:", firstName.toString())
        Log.d("Before validate:", lastName.toString())
        Log.d("Before validate:", email.toString())
        Log.d("Before validate:", password.toString())
        Log.d("Before validate:", username.toString())
        Log.d("Before validate:", phone.toString())

        if (firstName != null && firstName.length < 3) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.FIRST_NAME_LENGTH
            )
            flag = false
        } else if (lastName != null && lastName.length < 3) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.LAST_NAME_LENGTH
            )
            flag = false

        } else if (email != null && !email.isEmailFormatted()) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.EMAIL_FORMAT
            )
            flag = false
        } else if (password != null && !password.isPasswordFormatted()) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.PASSWORD
            )
            flag = false
        } else if (username != null && !username.isUsername()) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.USERNAME
            )
            flag = false
        } else if (phone != null && !phone.isPhoneNumber()) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.PHONE
            )
            flag = false
        }

        Log.d("After validate:", flag.toString())
        if (flag)
            _signUpState.value = SignUpState(
                validate = Validate.IDLE
            )
        return flag
    }

}