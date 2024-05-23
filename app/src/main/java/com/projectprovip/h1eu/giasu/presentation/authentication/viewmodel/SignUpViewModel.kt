package com.projectprovip.h1eu.giasu.presentation.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.presentation.common.isEmailFormatted
import com.projectprovip.h1eu.giasu.presentation.common.isPasswordFormatted
import com.projectprovip.h1eu.giasu.presentation.common.isPhoneNumber
import com.projectprovip.h1eu.giasu.presentation.common.isUsername
import com.projectprovip.h1eu.giasu.domain.authentication.model.UserSignUpParams
import com.projectprovip.h1eu.giasu.domain.authentication.usecase.SignUpUseCase
import com.projectprovip.h1eu.giasu.domain.location.usecase.GetProvinceUseCase
import com.projectprovip.h1eu.giasu.presentation.authentication.model.ProvinceState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.SignUpState
import com.projectprovip.h1eu.giasu.presentation.authentication.model.Validate
import com.projectprovip.h1eu.giasu.presentation.authentication.model.toProvinceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getProvinceUseCase: GetProvinceUseCase
) : ViewModel() {

    private var _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState
    private var _provinceState = mutableStateOf(ProvinceState())
    val provinceState: State<ProvinceState> = _provinceState

    fun getProvince() {
        getProvinceUseCase().onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _provinceState.value = ProvinceState(isLoading = true)
                }

                is EDSResult.Error -> {
                    Log.e("SignUpViewModel", result.message ?: "Unexpected error")
                    _provinceState.value =
                        ProvinceState(error = result.message ?: "Unexpected error")
                }

                is EDSResult.Success -> {
                    val provinceItem = result.data!!.map {
                        it.toProvinceItem()
                    }
                    _provinceState.value = ProvinceState(
                        province = provinceItem
                    )
                    Log.d("SignUpViewModel", _provinceState.value.toString())

                }
            }
        }.launchIn(viewModelScope)
    }

    fun signUp(input: UserSignUpParams) {
        Log.d("SignUpViewModel", input.toString())
        signUpUseCase(input).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _signUpState.value = _signUpState.value.copy(isLoading = true)
                }

                is EDSResult.Error -> {
                    _signUpState.value =
                        SignUpState(error = result.message ?: "Unexpected error")
                    Log.e("SignUpViewModel", result.message ?: "Unexpected error")
                    Log.d("SignUpViewModel", _signUpState.value.toString())

                }

                is EDSResult.Success -> {
                    _signUpState.value = SignUpState(
                        user = result.data!!.user,
                        token = result.data.token
                    )
                    Log.d("SignUpViewModel", _signUpState.value.toString())

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
        birthYear: String? = null,
        city: String? = null
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
        } else if (birthYear != null  && birthYear.isEmpty()) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.BIRTH_YEAR
            )
            flag = false
        } else if (city != null && city.isEmpty()) {
            //To do
            _signUpState.value = SignUpState(
                validate = Validate.CITY
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