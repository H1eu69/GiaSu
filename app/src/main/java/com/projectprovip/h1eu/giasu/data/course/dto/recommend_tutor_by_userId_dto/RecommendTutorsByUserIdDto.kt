package com.projectprovip.h1eu.giasu.data.course.dto.recommend_tutor_by_userId_dto

data class RecommendTutorsByUserIdDto(
    val current_page: Int,
    val `data`: List<String>,
    val total_pages: Int
)