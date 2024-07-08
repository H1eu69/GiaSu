package com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto

data class RecommendedCoursesDto(
    val current_page: Int,
    val `data`: List<String>,
    val total_pages: Int
)