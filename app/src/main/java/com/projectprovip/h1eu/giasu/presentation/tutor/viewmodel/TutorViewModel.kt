package com.projectprovip.h1eu.giasu.presentation.tutor.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.domain.tutor.usecase.GetAllTutorUseCase
import com.projectprovip.h1eu.giasu.presentation.tutor.model.TutorListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TutorViewModel @Inject constructor(
    private val getAllTutorUseCase: GetAllTutorUseCase
) : ViewModel() {

    private var _state = mutableStateOf(TutorListState())
    val state: State<TutorListState> = _state

    private var _currentPage = 1

    init {
        getAllTutorUseCase(_currentPage).onEach {
            when (it) {
                is Result.Loading -> {
                    _state.value = TutorListState(isLoading = true)
                }

                is Result.Error -> {
                    _state.value = TutorListState(error = it.message.toString())
                }

                is Result.Success -> {
                    _state.value = TutorListState(data = it.data!!)
                    Log.d("Test success", "at $_currentPage")
                    _currentPage++
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadMore() {
        val currentData = _state.value.data.toMutableList()
        getAllTutorUseCase(_currentPage).onEach {
            when (it) {
                is Result.Loading -> {}
                is Result.Error -> {}
                is Result.Success -> {
                    currentData.addAll(it.data!!)
                    _state.value = TutorListState(data = currentData)
                    Log.d("Test success", "at $_currentPage")
                    _currentPage++
                }
            }
            Log.d("Test _currentPage", _currentPage.toString())
        }.launchIn(viewModelScope)
    }
}