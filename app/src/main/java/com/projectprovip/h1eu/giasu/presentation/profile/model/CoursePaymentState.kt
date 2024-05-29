package com.projectprovip.h1eu.giasu.presentation.profile.model

data class CoursePaymentState(
    val isLoading: Boolean = false,
    val courses: List<CoursePaymentModel> = emptyList(),
    val error: String = ""
)

data class CoursePaymentModel (
    val code: String = "000000",
    val courseId: String = "dsadasdas-ds-gsfdsfsfgsqrq-44848",
    val courseTitle: String= "Khoa hoc Java pro vip 123 456 789 10 12 12",
    val paymentId: String= "dsadasdas-ds-gsfdsfsfgsqrq-44848",
    val paymentStatus: String= "Completed",
)