package com.projectprovip.h1eu.giasu.presentation.home.model

data class SearchSuggestState(
    val isLoading: Boolean = false,
    val data: List<String> = emptyList(),
    val error: String = ""
)