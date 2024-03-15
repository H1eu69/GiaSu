package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.common.alphaNumericOnly
import com.projectprovip.h1eu.giasu.domain.course.usecase.RegisterCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val registerCourseUseCase: RegisterCourseUseCase
)  : ViewModel() {

    private var _courseRegisterState = mutableStateOf(CourseRegisterState())
    val courseRegisterState : State<CourseRegisterState> =  _courseRegisterState
    fun registerCourse(courseId: String, token: String) {
        registerCourseUseCase(courseId, token).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _courseRegisterState.value = CourseRegisterState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _courseRegisterState.value = CourseRegisterState(error = true, message = result.message.toString().alphaNumericOnly())
                    Log.d("HomeVM", result.message.toString())
                }

                is EDSResult.Success -> {
                    val courses = result.data!!
                    Log.d("HomeVM", courses.toString())
                    _courseRegisterState.value = CourseRegisterState(isSuccess = true, message = "Course register successful")
                }
            }
        }.launchIn(viewModelScope)
    }
}