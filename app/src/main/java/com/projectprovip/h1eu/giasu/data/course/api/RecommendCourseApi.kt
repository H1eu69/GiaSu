package com.projectprovip.h1eu.giasu.data.course.api

import com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto.RecommendedCoursesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendCourseApi {
    @GET("/{id}")
    suspend fun getRecommendedCourses(
        @Path("id") id: String,
    ) : Response<RecommendedCoursesDto>
}