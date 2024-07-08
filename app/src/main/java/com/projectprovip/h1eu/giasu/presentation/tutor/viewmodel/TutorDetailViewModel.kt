package com.projectprovip.h1eu.giasu.presentation.tutor.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.GetTutorDetailUseCase
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.RequestTutorParams
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.RequestTutorUseCase
import com.projectprovip.h1eu.giasu.presentation.tutor.model.RequestTutorState
import com.projectprovip.h1eu.giasu.presentation.tutor.model.TutorDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TutorDetailViewModel @Inject constructor(
    private val getTutorDetailUseCase: GetTutorDetailUseCase,
    private val requestTutorUseCase: RequestTutorUseCase
) : ViewModel() {
    private var _state = mutableStateOf(TutorDetailState())
    val state: State<TutorDetailState> = _state

    private var _requestState = mutableStateOf(RequestTutorState())
    val requestState: State<RequestTutorState> = _requestState

    fun getTutorDetail(tutorId: String) {
        getTutorDetailUseCase(tutorId).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    Log.d("Test Loading", "load from vm")
                    _state.value = TutorDetailState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = TutorDetailState(error = result.message.toString())
                }

                is EDSResult.Success -> {
                    result.data?.let {
                        _state.value = TutorDetailState(data = it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendRequestTutor(auth:String, params: RequestTutorParams) {
        requestTutorUseCase(auth, params).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    Log.d("Test Loading", "load from vm")
                    _requestState.value = RequestTutorState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _requestState.value = RequestTutorState(error = result.message.toString())
                }

                is EDSResult.Success -> {
                    _requestState.value = RequestTutorState(isSuccess = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}