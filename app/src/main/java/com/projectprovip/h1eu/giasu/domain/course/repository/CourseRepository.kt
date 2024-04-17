package com.projectprovip.h1eu.giasu.domain.course.repository

import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.LearningCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.CourseByIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course_detail_dto.LearningCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.NewCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.request_course.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.review_tutor.ReviewTutorDto
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import retrofit2.Response

interface CoursesRepository {
    suspend fun getAllClasses(page: Int, subjectName: String?): NewCoursesDto
    suspend fun getCourseById(id: String, ): CourseByIdDto
    suspend fun createCourse(token: String, input: CreateCourseParams): Response<Unit>
    suspend fun registerCourse(id: String, token: String?): RequestCourseDto
    suspend fun getRequestedCourse(token: String?): RequestCourseDto
    suspend fun getRequestedCourseDetail(
        id: String,
        token: String?
    ): RequestedCourseDetailDto

    suspend fun getLearningCourses(token: String): LearningCourseDto
    suspend fun getLearningCourseDetail(
        token: String,
        courseId: String
    ): LearningCourseDetailDto

    suspend fun reviewTutorByCourseId(
        token: String,
        courseId: String,
        input: ReviewTutorInput
    ): ReviewTutorDto
}