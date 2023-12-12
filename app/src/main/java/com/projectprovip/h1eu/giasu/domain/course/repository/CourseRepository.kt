package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.CoursesInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.LearningCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import retrofit2.Response

interface CoursesRepository {
    suspend fun getAllClasses(): CoursesInformationDto
    suspend fun registerCourse(id: Int, token: String?): Response<RequestCourseDto>
    suspend fun getRequestedCourse(token: String?): Response<RequestedCourseDto>
    suspend fun getRequestedCourseDetail(id: Int, token: String?): Response<RequestedCourseDetailDto>
    suspend fun getLearningCourses(token: String): Response<LearningCoursesDto>
    suspend fun reviewTutorByCourseId(token: String, courseId: Int, input: ReviewTutorInput): Response<Unit>
}