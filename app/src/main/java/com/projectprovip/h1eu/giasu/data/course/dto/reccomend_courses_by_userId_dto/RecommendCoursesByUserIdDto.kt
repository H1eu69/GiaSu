package com.projectprovip.h1eu.giasu.data.course.dto.reccomend_courses_by_userId_dto

data class RecommendCoursesByUserIdDto(
    val current_page: Int,
    val `data`: List<String>,
    val total_pages: Int
)