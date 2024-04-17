package com.projectprovip.h1eu.giasu.data.course.repository

import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.LearningCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.NewCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.CourseByIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course_detail_dto.LearningCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.request_course.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.review_tutor.ReviewTutorDto
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import retrofit2.Response
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi
) : CoursesRepository{
    override suspend fun getAllClasses(page: Int, subjectName: String?): NewCoursesDto {
        return api.getAllClasses(page, subjectName)
    }

    override suspend fun getCourseById(id: String): CourseByIdDto {
        return api.getCourseById(id)
    }

    override suspend fun createCourse(token: String, input: CreateCourseParams): Response<Unit> {
        return api.createCourse(token, input)
    }

    override suspend fun registerCourse(id: String, token: String?): RequestCourseDto {
        return api.registerCourse(id, token!!)
    }

    override suspend fun getRequestedCourse(token: String?): RequestCourseDto {
        return api.getRequestedCourse(token!!)
    }

    override suspend fun getRequestedCourseDetail(
        id: String,
        token: String?
    ): RequestedCourseDetailDto {
        return api.getRequestedCourseDetail(id, token!!)
    }

    override suspend fun getLearningCourses(token: String): LearningCourseDto {
        return api.getLearningCourses(token)
    }

    override suspend fun getLearningCourseDetail(
        token: String,
        courseId: String
    ): LearningCourseDetailDto {
        return api.getLearningCourseDetail(token, courseId)
    }

    override suspend fun reviewTutorByCourseId(token: String, courseId: String, input: ReviewTutorInput) : ReviewTutorDto {
        return api.reviewTutorOfByCourseId(token, courseId, input)
    }
}