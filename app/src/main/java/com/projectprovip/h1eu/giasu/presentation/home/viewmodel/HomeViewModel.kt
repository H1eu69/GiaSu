package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase
) : ViewModel() {
    private var _courseState = mutableStateOf(CourseDetailState())
    val courseState : State<CourseDetailState> =  _courseState
    init {
        getAllClasses()
    }
    private fun getAllClasses() {
        getCourseUseCase().onEach { result ->
            when (result) {
                is Result.Loading -> {
                    _courseState.value = CourseDetailState(isLoading = true)
                }

                is Result.Error -> {
                    _courseState.value = CourseDetailState(error = result.message)
                }

                is Result.Success -> {
                    val courses = result.data!!

                    _courseState.value = CourseDetailState(data = courses)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getClassDetailById(id: Int) = _courseState.value.data.find {
        it.id == id
    }
}