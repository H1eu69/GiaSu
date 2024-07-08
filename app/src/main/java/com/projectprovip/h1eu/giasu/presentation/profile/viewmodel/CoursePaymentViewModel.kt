package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCoursesPaymentUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import toCoursePaymentModel
import javax.inject.Inject

@HiltViewModel
class CoursePaymentViewModel @Inject constructor(
    private val getCoursesPaymentUseCase: GetCoursesPaymentUseCase
) : ViewModel() {
    private var _state = mutableStateOf(CoursePaymentState())
    val state: State<CoursePaymentState> = _state

    fun getCoursesPayment(token: String) {
        getCoursesPaymentUseCase(token).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _state.value = CoursePaymentState(isLoading = true)
                }

                is EDSResult.Error -> {
                    _state.value = CoursePaymentState(error = result.message!!)
                    Log.e("CoursePayment", "getCoursesPayment: ${result.message}")
                }

                is EDSResult.Success -> {
                    val data = result.data!!.map {
                        it.toCoursePaymentModel()
                    }
                    Log.d("CoursePayment", "getCoursesPayment: ${data}")
                    _state.value = CoursePaymentState(courses = data)
                }
            }
        }.launchIn(viewModelScope)
    }
}