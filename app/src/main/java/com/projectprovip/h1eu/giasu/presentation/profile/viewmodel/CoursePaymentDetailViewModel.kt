package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.toCoursePaymentDetail
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseByIdUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCoursesPaymentUseCase
import com.projectprovip.h1eu.giasu.domain.course.usecase.NotifyCoursePaymentUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentDetailState
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentState
import com.projectprovip.h1eu.giasu.presentation.profile.model.NotifyCoursePaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoursePaymentDetailViewModel @Inject constructor(
    private val getCourseByIdUseCase: GetCourseByIdUseCase,
    private val notifyCoursePaymentUseCase: NotifyCoursePaymentUseCase
) : ViewModel() {
    private var _state = mutableStateOf(CoursePaymentDetailState())
    val state: State<CoursePaymentDetailState> = _state

    private var _state2 = mutableStateOf(NotifyCoursePaymentState())
    val state2: State<NotifyCoursePaymentState> = _state2

    fun getCourseDetail(id: String) {
        getCourseByIdUseCase(id).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value = CoursePaymentDetailState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = CoursePaymentDetailState(error = result.message!!)
                    Log.e("CoursePaymentDetail", "getCoursesPayment: ${result.message}")
                }

                is EDSResult.Success -> {
                    val data = result.data!!.value.toCoursePaymentDetail()
                    Log.d("CoursePaymentDetail", "getCoursesPayment: ${data}")
                    _state.value = CoursePaymentDetailState(courses = data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun notifyCoursePayment(id: String,token: String) {
        notifyCoursePaymentUseCase(id,token).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state2.value = NotifyCoursePaymentState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state2.value = NotifyCoursePaymentState(error = result.message!!)
                    Log.e("CoursePaymentDetail", "getCoursesPayment: ${result.message}")
                }

                is EDSResult.Success -> {
                    _state2.value = NotifyCoursePaymentState(isSuccess = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}