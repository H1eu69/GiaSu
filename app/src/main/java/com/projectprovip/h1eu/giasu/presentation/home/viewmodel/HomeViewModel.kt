package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.RegisterCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
    private val registerCourseUseCase: RegisterCourseUseCase
) : ViewModel() {
    private var _courseDetailState = mutableStateOf(CourseDetailState())
    val courseDetailState: State<CourseDetailState> = _courseDetailState
    var _currentPageIndex = 1
    var _showLoading = true

    init {
        getCourses()
    }

    fun getCourses() {
        val currentData = _courseDetailState.value.data.toMutableList()
        getCourseUseCase(_currentPageIndex).onEach {
            when (it) {
                is EDSResult.Loading -> {
                    if (_showLoading) {
                        _courseDetailState.value = CourseDetailState(isLoading = true)
                        _showLoading = false
                    }
                }
                is EDSResult.Error -> {
                    _courseDetailState.value = CourseDetailState(error = it.message)

                }

                is EDSResult.Success -> {
                    currentData.addAll(it.data!!)
                    _courseDetailState.value = CourseDetailState(data = currentData)
                    _currentPageIndex++
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getClassDetailById(id: String) = _courseDetailState.value.data.find {
        it.id == id
    }
}