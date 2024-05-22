package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.reccomend_courses_by_userId_dto.RecommendCoursesByUserIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto.RecommendedCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_tutor_by_userId_dto.RecommendTutorsByUserIdDto
import retrofit2.Response

interface RecommendCourseRepository {
    suspend fun getRecommendCourse(id: String): Response<RecommendedCoursesDto>
    suspend fun getRecommendCoursesByUserId(userId: String): Response<RecommendCoursesByUserIdDto>
    suspend fun getRecommendTutorsByUserId(userId: String): Response<RecommendTutorsByUserIdDto>

}