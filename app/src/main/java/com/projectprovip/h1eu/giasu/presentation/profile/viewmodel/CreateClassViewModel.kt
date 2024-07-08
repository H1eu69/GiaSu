package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.domain.course.usecase.CreateCourseUseCase
import com.projectprovip.h1eu.giasu.domain.subject.model.toSubjectItem
import com.projectprovip.h1eu.giasu.domain.subject.usecase.GetSubjectUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.CreateCourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateClassViewModel @Inject constructor(
    private val createCourseUseCase: CreateCourseUseCase,
    private val getSubjectUseCase: GetSubjectUseCase
) : ViewModel() {
    private var _state = mutableStateOf(CreateCourseState())
    val state: State<CreateCourseState> = _state

    init {
        getSubjects()
    }

    fun requestClass(token: String, input: CreateCourseParams) {
        Log.d("Test create course", input.toString())

        createCourseUseCase(token, input).onEach { result ->
            val subject = _state.value.subjects
            when (result) {
                is EDSResult.Loading -> {

                    _state.value = CreateCourseState(
                        isLoading = true,
                        subjects = subject
                    )
                    Log.d("Test create course", "loading")
                }

                is EDSResult.Error -> {
                    _state.value = CreateCourseState(message = result.message!!, subjects = subject)
                    Log.d("Test create course", result.message!!)
                }

                is EDSResult.Success -> {
                    _state.value = CreateCourseState(
                        isSuccessful = true,
                        message = "Create successfully, please wait for review", subjects = subject
                    )
                    Log.d("Test create course", "Success")
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getSubjects() {
        getSubjectUseCase().onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                }

                is EDSResult.Error -> {
                    _state.value = CreateCourseState(message = result.message!!)
                    Log.d("Test getSubjects course", result.message)
                }

                is EDSResult.Success -> {
                    _state.value = CreateCourseState(
                        subjects = result.data!!.map {
                            it.toSubjectItem()
                        }
                    )
                }
            }

        }.launchIn(viewModelScope)
    }
}