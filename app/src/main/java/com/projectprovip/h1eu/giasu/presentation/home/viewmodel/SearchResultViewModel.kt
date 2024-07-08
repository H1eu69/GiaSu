package com.projectprovip.h1eu.giasu.presentation.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.domain.course.usecase.GetCourseUseCase
import com.projectprovip.h1eu.giasu.presentation.home.model.SearchResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
) : ViewModel() {
    private var _searchResultState = mutableStateOf(SearchResultState())
    val searchResultState: State<SearchResultState> = _searchResultState

    var _currentPageIndex = 1
    fun getCourses(subjectName: String) {
        val currentData = _searchResultState.value.data.toMutableList()
        getCourseUseCase(_currentPageIndex, subjectName).onEach {
            when (it) {
                is EDSResult.Loading -> {
                    _searchResultState.value = SearchResultState(isLoading = true)

                }
                is EDSResult.Error -> {
                    _searchResultState.value = SearchResultState(error = it.message)

                }

                is EDSResult.Success -> {
                    currentData.addAll(it.data!!)
                    _searchResultState.value = SearchResultState(data = currentData)
                    _currentPageIndex++
                }
            }
        }.launchIn(viewModelScope)
    }
}