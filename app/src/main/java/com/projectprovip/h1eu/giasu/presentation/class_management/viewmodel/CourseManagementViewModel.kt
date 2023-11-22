package com.projectprovip.h1eu.giasu.presentation.class_management.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetRequestedCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.class_management.model.RequestedCourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CourseManagementViewModel @Inject constructor(
    private val getRequestedCourseUseCase: GetRequestedCourseUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RequestedCourseState())
    val state : State<RequestedCourseState> = _state
    var filteredList : MutableState<List<RequestedCourse>> = mutableStateOf( emptyList())

    fun getRequestedCourses(token: String) {
        getRequestedCourseUseCase(token).onEach { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("course loading", result.message.toString())
                    _state.value = RequestedCourseState(isLoading = true)
                }
                is Result.Error -> {
                    Log.d("course error", result.message.toString());
                    _state.value = RequestedCourseState(message = result.message.toString())
                }
                is Result.Success -> {
                    Log.d("course management", result.data!!.toString())
                    _state.value = RequestedCourseState(data = result.data)
                    filteredList.value = result.data
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getListByFilter(status: String) {
        filteredList.value = if(status == "All") {
            _state.value.data
        } else {
            _state.value.data.filter {
                it.requestStatus == status
            }
        }

    }
}