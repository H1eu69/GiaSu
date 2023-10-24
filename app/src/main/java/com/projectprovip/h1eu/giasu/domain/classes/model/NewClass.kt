package com.projectprovip.h1eu.giasu.domain.classes.model

data class NewClass (
    val title: String,
    val id: String,
    val sessionPerWeek: Int,
    val minutePerSession: Int,
    val address: String,
    val fee: Int,
    val creationTime: String,
    val description: String
)