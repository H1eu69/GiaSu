package com.projectprovip.h1eu.giasu.data.subject.dto

data class SubjectDto(
    val displayMessage: String,
    val error: Error,
    val isFailure: Boolean,
    val isSuccess: Boolean,
    val value: List<Value>
)