package com.projectprovip.h1eu.giasu.presentation.class_management.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetRequestedCourseDetailUseCase
import com.projectprovip.h1eu.giasu.presentation.class_management.model.RequestedCourseDetailState
import com.projectprovip.h1eu.giasu.presentation.class_management.model.RequestedCourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestedCourseDetailViewModel @Inject constructor(
    private val requestedCourseDetailUseCase: GetRequestedCourseDetailUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RequestedCourseDetailState())
    val state : State<RequestedCourseDetailState> = _state

    fun getRequestedCourseDetail(id: Int, token: String) {
        requestedCourseDetailUseCase(id, token).onEach { result ->
            when(result) {
                is Result.Loading -> {
                    Log.d("course loading detail", result.message.toString())
                    _state.value = RequestedCourseDetailState(isLoading = true)
                }

                is Result.Error -> {
                    _state.value = RequestedCourseDetailState(message = result.message.toString())
                }

                is Result.Success -> {
                    Log.d("course management detail", result.data!!.toString())
                    _state.value = RequestedCourseDetailState(data = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}