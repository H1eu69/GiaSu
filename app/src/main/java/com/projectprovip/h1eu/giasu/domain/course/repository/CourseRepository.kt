package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.LearningCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.LearningCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.CourseByIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.NewCoursesDto
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseInput
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import retrofit2.Response

interface CoursesRepository {
    suspend fun getAllClasses(page: Int, subjectName: String?): NewCoursesDto
    suspend fun getCourseById(id: String, ): CourseByIdDto
    suspend fun createCourse(token: String, input: CreateCourseInput): Response<Unit>
    suspend fun registerCourse(id: String, token: String?): Response<RequestCourseDto>
    suspend fun getRequestedCourse(token: String?): Response<RequestedCourseDto>
    suspend fun getRequestedCourseDetail(
        id: Int,
        token: String?
    ): Response<RequestedCourseDetailDto>

    suspend fun getLearningCourses(token: String): Response<LearningCourseDto>
    suspend fun getLearningCourseDetail(
        token: String,
        courseId: String
    ): Response<LearningCourseDetailDto>

    suspend fun reviewTutorByCourseId(
        token: String,
        courseId: String,
        input: ReviewTutorInput
    ): Response<Unit>
}