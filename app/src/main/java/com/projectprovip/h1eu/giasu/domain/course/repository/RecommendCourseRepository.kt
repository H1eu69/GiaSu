package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto.RecommendedCoursesDto
import retrofit2.Response

interface RecommendCourseRepository {
    suspend fun getRecommendCourse(id: String): Response<RecommendedCoursesDto>
}