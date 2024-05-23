package com.projectprovip.h1eu.giasu.data.course.repository

import com.projectprovip.h1eu.giasu.data.course.api.RecommendCourseApi
import com.projectprovip.h1eu.giasu.data.course.dto.reccomend_courses_by_userId_dto.RecommendCoursesByUserIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto.RecommendedCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_tutor_by_userId_dto.RecommendTutorsByUserIdDto
import com.projectprovip.h1eu.giasu.domain.course.repository.RecommendCourseRepository
import retrofit2.Response
import javax.inject.Inject

class RecommendCoursesRepositoryImpl @Inject constructor(
    private val api: RecommendCourseApi
) : RecommendCourseRepository{
    override suspend fun getRecommendCourse(id: String): Response<RecommendedCoursesDto> {
        return api.getRecommendedCourses(id)
    }

    override suspend fun getRecommendCoursesByUserId(userId: String): Response<RecommendCoursesByUserIdDto> {
        return api.getRecommendedCoursesByUserId(userId)
    }

    override suspend fun getRecommendTutorsByUserId(userId: String): Response<RecommendTutorsByUserIdDto> {
        return api.getRecommendedTutorsByUserId(userId)
    }
}