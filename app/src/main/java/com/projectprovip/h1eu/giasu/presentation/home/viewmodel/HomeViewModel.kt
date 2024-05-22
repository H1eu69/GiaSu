package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetRecommendCoursesByUserIdUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetRecommendTutorsByUserIdUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.RegisterCourseUseCase
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.GetAllTutorUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.HomeState
import com.projectprovip.h1eu.giasu.presentation.home.model.TutorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
    private val getTutorUseCase: GetAllTutorUseCase,
    private val getRecommendCoursesByUserIdUseCase: GetRecommendCoursesByUserIdUseCase,
    private val getRecommendTutorsByUserIdUseCase: GetRecommendTutorsByUserIdUseCase,
    private val registerCourseUseCase: RegisterCourseUseCase
) : ViewModel() {
    private var _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _tutorState = mutableStateOf(TutorState())
    val tutorState: State<TutorState> = _tutorState

    private var _recommendCoursesId = mutableListOf<String>()
    private var _recommendTutorsId = mutableListOf<String>()

    var _currentPageIndex = 1
    var _showLoading = true

    init {
        getCoursesAndIncreaseIndex()
    }

    fun getRecommendedCoursesAndTutors(userId: String) {
        Log.d("getRecommendedCoursesAndTutors", "userId: $userId")
        viewModelScope.launch {
            getRecommendCoursesByUserIdUseCase(userId).onEach {
                when (it) {
                    is EDSResult.Loading -> {

                    }
                    is EDSResult.Error -> {
                        Log.e("Error getRecommendCoursesByUserIdUseCase", it.message!!)
                    }

                    is EDSResult.Success -> {
                        _recommendCoursesId.clear()
                        _recommendCoursesId.addAll(it.data!!)
                        Log.d("RecommendCourses", _recommendCoursesId.toString())
                    }
                }
            }.launchIn(this)


            getRecommendTutorsByUserIdUseCase(userId).onEach {
                when (it) {
                    is EDSResult.Loading -> {

                    }
                    is EDSResult.Error -> {
                        Log.e("Error getRecommendTutorsByUserIdUseCase", it.message!!)
                    }

                    is EDSResult.Success -> {
                        _recommendTutorsId.clear()
                        _recommendTutorsId.addAll(it.data!!)
                        Log.d("RecommendTutors", _recommendTutorsId.toString())
                    }
                }
            }.launchIn(this)
        }
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

    fun getTutors(subject: String? = null) {
        getTutorUseCase(subject = subject).onEach {
            when (it) {
                is EDSResult.Loading -> {
                    if (_showLoading) {
                        _tutorState.value = TutorState(isLoading = true)
                        _showLoading = false
                    }
                }
                is EDSResult.Error -> {
                    _tutorState.value = TutorState(error = it.message)
                    Log.e("Error HomeVM", it.message!!)
                }

                is EDSResult.Success -> {
                    _tutorState.value = TutorState(data = it.data!!.shuffled())
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