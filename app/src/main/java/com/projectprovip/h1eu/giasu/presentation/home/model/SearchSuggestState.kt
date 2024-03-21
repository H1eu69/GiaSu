package com.projectprovip.h1eu.giasu.presentation.home.model

data class SearchSuggestState(
    val isLoading: Boolean = false,
    val data: List<String> = listOf(
        "java programming",
        "python programming",
        "basic guitar",
        "Nguyen Bao Chau",
        "swimming",
        "karate",
        "communicate english",
        "piano",
        "history",
        "physics",
        "advanced physics",
    ),
    val error: String = ""
)