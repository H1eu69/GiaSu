package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.profile.usecase.GetUserProfileUseCase
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileParams
import com.projectprovip.h1eu.giasu.domain.profile.usecase.UpdateProfileUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private var _state = mutableStateOf(UpdateProfileState())
    val state: State<UpdateProfileState> = _state

    fun getProfile(token: String) {
        getUserProfileUseCase(token).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value = UpdateProfileState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = UpdateProfileState(error = result.message!!)
                    Log.d("Test get profile error", result.message)
                }

                is EDSResult.Success -> {
                    _state.value = UpdateProfileState(data = result.data!!)
                    Log.d("Test get profile", result.data.toString())

                }
            }

        }.launchIn(viewModelScope)
    }

    fun updateProfile(token: String, updateProfileParams: UpdateProfileParams) {
        updateProfileUseCase(token, updateProfileParams).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value = UpdateProfileState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = UpdateProfileState(error = result.message!!)
                    Log.d("Test get profile error", result.message)
                }

                is EDSResult.Success -> {
                    _state.value = UpdateProfileState(isUpdateDone = true)
                    Log.d("Test get profile", result.data.toString())

                }
            }

        }.launchIn(viewModelScope)
    }
}