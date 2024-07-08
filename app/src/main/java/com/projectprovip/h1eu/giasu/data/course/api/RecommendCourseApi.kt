package com.projectprovip.h1eu.giasu.data.course.api

import com.projectprovip.h1eu.giasu.data.course.dto.reccomend_courses_by_userId_dto.RecommendCoursesByUserIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto.RecommendedCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_tutor_by_userId_dto.RecommendTutorsByUserIdDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendCourseApi {
    @GET("/get_recommend_subject/{id}")
    suspend fun getRecommendedCourses(
        @Path("id") id: String,
    ) : Response<RecommendedCoursesDto>


    @GET("/get_recommend_courses/{id}")
    suspend fun getRecommendedCoursesByUserId(
        @Path("id") userId: String,
    ) : Response<RecommendCoursesByUserIdDto>


    @GET("/get_recommend_tutors/{id}")
    suspend fun getRecommendedTutorsByUserId(
        @Path("id") userId: String,
    ) : Response<RecommendTutorsByUserIdDto>
}