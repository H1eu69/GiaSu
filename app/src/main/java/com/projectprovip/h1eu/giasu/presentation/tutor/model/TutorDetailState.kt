package com.projectprovip.h1eu.giasu.presentation.tutor.model

import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor
import com.projectprovip.h1eu.giasu.domain.tutor.model.TutorDetail

data class TutorDetailState(
    val isLoading: Boolean = false,
    val data: TutorDetail = TutorDetail(),
    val error: String = ""
)

data class RequestTutorState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = ""
)