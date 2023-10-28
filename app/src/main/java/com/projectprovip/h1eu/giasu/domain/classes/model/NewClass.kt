package com.projectprovip.h1eu.giasu.domain.classes.model

data class NewClass (
    val title: String,
    val id: Int,
    val sessionPerWeek: Int,
    val minutePerSession: Int,
    val address: String,
    val fee: Double,
    val creationTime: String,
    val description: String
)