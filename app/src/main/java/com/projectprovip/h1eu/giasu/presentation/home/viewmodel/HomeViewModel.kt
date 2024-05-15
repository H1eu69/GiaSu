package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.RegisterCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
    private val registerCourseUseCase: RegisterCourseUseCase
) : ViewModel() {
    private var _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState
    var _currentPageIndex = 1
    var _showLoading = true

    init {
        getCoursesAndIncreaseIndex()
    }

    fun getCourses() {
        _currentPageIndex = 1
        getCourseUseCase(_currentPageIndex).onEach {
            when (it) {
                is EDSResult.Loading -> {
                    if (_showLoading) {
                        _homeState.value = HomeState(isLoading = true)
                        _showLoading = false
                    }
                }
                is EDSResult.Error -> {
                    _homeState.value = HomeState(error = it.message)
                    Log.e("Error HomeVM", it.message!!)
                }

                is EDSResult.Success -> {
                    _homeState.value = HomeState(data = it.data!!.shuffled())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCoursesAndIncreaseIndex() {
        val currentData = _homeState.value.data.toMutableList()
        getCourseUseCase(_currentPageIndex).onEach {
            when (it) {
                is EDSResult.Loading -> {
                    if (_showLoading) {
                        _homeState.value = HomeState(isLoading = true)
                        _showLoading = false
                    }
                }
                is EDSResult.Error -> {
                    _homeState.value = HomeState(error = it.message)
                    Log.e("Error HomeVM", it.message!!)
                }

                is EDSResult.Success -> {
                    currentData.addAll(it.data!!)
                    _homeState.value = HomeState(data = currentData)
                    _currentPageIndex++
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getClassDetailById(id: String) = _homeState.value.data.find {
        it.id == id
    }
}