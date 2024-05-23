package com.projectprovip.h1eu.giasu.data.tutor.dto.tutorDto

data class Value(
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val items: List<Item>,
    val pageIndex: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int
)