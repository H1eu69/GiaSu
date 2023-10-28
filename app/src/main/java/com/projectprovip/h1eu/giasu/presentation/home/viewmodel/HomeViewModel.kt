package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.domain.classes.usecase.GetClassUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.NewClassesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClassUseCase: GetClassUseCase
) : ViewModel() {
    private var pageIndex = 1
    private var _newClassesState = mutableStateOf(NewClassesState())
    val newClassesState : State<NewClassesState> =  _newClassesState
    init {
        getAllClasses()
    }
    private fun getAllClasses() {
        getClassUseCase().onEach { result ->
            when (result) {
                is Result.Loading -> {
                    _newClassesState.value = NewClassesState(isLoading = true)
                }

                is Result.Error -> {
                    _newClassesState.value = NewClassesState(error = result.message)
                }

                is Result.Success -> {
                    pageIndex++
                    _newClassesState.value = NewClassesState(data = result.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}