package com.projectprovip.h1eu.giasu.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.common.alphaNumericOnly
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetLearningCourseDetailUseCase
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.ReviewTutorUseCase
import com.projectprovip.h1eu.giasu.presentation.profile.model.LearningCourseDetailState
import com.projectprovip.h1eu.giasu.presentation.profile.model.ReviewTutorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TutorReviewViewModel @Inject constructor(
    private val reviewTutorUseCase: ReviewTutorUseCase,
    private val getLearningCourseDetailUseCase: GetLearningCourseDetailUseCase
) : ViewModel() {
    private var _reviewTutorState = mutableStateOf(ReviewTutorState())
    val reviewTutorState: State<ReviewTutorState> = _reviewTutorState

    private var _learningCourseDetailState = mutableStateOf(LearningCourseDetailState())
    val learningCourseDetailState: State<LearningCourseDetailState> = _learningCourseDetailState

    fun sendReviewRequest(auth: String, courseId: String, reviewTutorInput: ReviewTutorInput) {
        reviewTutorUseCase(auth, courseId, reviewTutorInput).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _reviewTutorState.value = ReviewTutorState(isLoading = true)
                    Log.d("Test state", "loading")
                }

                is EDSResult.Error -> {
                    _reviewTutorState.value = ReviewTutorState(message = result.message!!.alphaNumericOnly())
                    Log.d("Test state", "error")
                }

                is EDSResult.Success -> {
                    _reviewTutorState.value = ReviewTutorState(
                        success = true,
                        message = "Review successfully"
                    )
                    Log.d("Test state", "success")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getLearningCourseData(auth: String, courseId: String) {
        getLearningCourseDetailUseCase(auth, courseId).onEach { result ->
            when (result) {
                is EDSResult.Loading -> {
                    _learningCourseDetailState.value = LearningCourseDetailState(isLoading = true)
                    Log.d("Test state review tutor", "loading")
                }

                is EDSResult.Error -> {
                    _learningCourseDetailState.value =
                        LearningCourseDetailState(message = result.message!!)
                    Log.d("Test state review tutor", result.message)
                }

                is EDSResult.Success -> {
                    _learningCourseDetailState.value = LearningCourseDetailState(
                        data = result.data!!,
                    )
                    Log.d("Test state review tutor", result.data.toString())
                    Log.d("Test state review tutor", _learningCourseDetailState.value.data.toString())
                    Log.d("Test state review tutor", "success")
                }
            }
        }.launchIn(viewModelScope)
    }
}