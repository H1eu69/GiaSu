package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.common.alphaNumericOnly
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.toCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseByIdUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetRecommendedCoursesUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.RegisterCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseDetailState
import com.projectprovip.h1eu.giasu.presentation.home.model.HomeState
import com.projectprovip.h1eu.giasu.presentation.home.model.CourseRegisterState
import com.projectprovip.h1eu.giasu.presentation.home.model.RecommendCoursesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val registerCourseUseCase: RegisterCourseUseCase,
    private val getCourseByIdUseCase: GetCourseByIdUseCase,
    private val getRecommendedCoursesUseCase: GetRecommendedCoursesUseCase,
)  : ViewModel() {

    private var _courseRegisterState = mutableStateOf(CourseRegisterState())
    val courseRegisterState : State<CourseRegisterState> =  _courseRegisterState

    private var _courseDetailState = mutableStateOf(CourseDetailState())
    val courseDetailState : State<CourseDetailState> =  _courseDetailState

    private var _recommendedCourseState = mutableStateOf(RecommendCoursesState())
    val recommendedCourseState : State<RecommendCoursesState> =  _recommendedCourseState

    fun getCourseById(courseId: String, ) {
        getCourseByIdUseCase(courseId,).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _courseDetailState.value = CourseDetailState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _courseDetailState.value = CourseDetailState(error = result.message)
                    Log.d("CourseDetailViewModel", result.message.toString())
                }

                is EDSResult.Success -> {
                    _courseDetailState.value = CourseDetailState(
                        data = result.data!!.value.toCourseDetail()
                    )
                    Log.d("CourseDetailViewModel", result.data!!.value.toCourseDetail().toString())
                }
            }
        }.launchIn(viewModelScope)
    }

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

    fun getRecommendedCourses(id: String) {
        getRecommendedCoursesUseCase(id,).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _recommendedCourseState.value = RecommendCoursesState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _recommendedCourseState.value = RecommendCoursesState(error = result.message)
                    Log.d("HomeVM", result.message.toString())
                }

                is EDSResult.Success -> {
                    val courses = result.data!!
                    Log.d("HomeVM", courses.toString())
//                    _recommendedCourseState.value = RecommendCoursesState( data = courses)
                }
            }
        }.launchIn(viewModelScope)
    }
}