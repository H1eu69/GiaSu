package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetLearningCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.ListLearningCourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LearningCoursesViewModel @Inject constructor(
    private val getLearningCourseUseCase: GetLearningCourseUseCase
) : ViewModel() {
    private var _state = mutableStateOf(ListLearningCourseState())
    val state: State<ListLearningCourseState> = _state
    fun getLearningCourse(token: String) {
        getLearningCourseUseCase(token).onEach { result ->
            when(result) {
                is EDSResult.Loading -> {
                    _state.value = ListLearningCourseState(isLoading = true)
                }
                is EDSResult.Error -> {
                    _state.value = ListLearningCourseState(error = result.message!!)
                }
                is EDSResult.Success -> {
                    _state.value = ListLearningCourseState(data = result.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}