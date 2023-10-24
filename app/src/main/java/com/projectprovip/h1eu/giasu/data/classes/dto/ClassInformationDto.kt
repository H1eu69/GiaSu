package com.projectprovip.h1eu.giasu.data.classes.dto

data class ClassInformationDto(
    val errors: List<Any>,
    val isFailed: Boolean,
    val isSuccess: Boolean,
    val reasons: List<Any>,
    val successes: List<Any>,
    val value: List<Value>,
    val valueOrDefault: List<ValueOrDefault>
)