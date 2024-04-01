package com.projectprovip.h1eu.giasu.data.course.repository

import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.dto.LearningCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.LearningCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.NewCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.CourseByIdDto
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseInput
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

    override suspend fun createCourse(token: String, input: CreateCourseInput): Response<Unit> {
        return api.createCourse(token, input)
    }

    override suspend fun registerCourse(id: String, token: String?): Response<RequestCourseDto> {
        return api.registerCourse(id, token!!)
    }

    override suspend fun getRequestedCourse(token: String?): Response<RequestedCourseDto> {
        return api.getRequestedCourse(token!!)
    }

    override suspend fun getRequestedCourseDetail(
        id: Int,
        token: String?
    ): Response<RequestedCourseDetailDto> {
        return api.getRequestedCourseDetail(id, token!!)
    }

    override suspend fun getLearningCourses(token: String): Response<LearningCourseDto> {
        return api.getLearningCourses(token)
    }

    override suspend fun getLearningCourseDetail(
        token: String,
        courseId: String
    ): Response<LearningCourseDetailDto> {
        return api.getLearningCourseDetail(token, courseId)
    }

    override suspend fun reviewTutorByCourseId(token: String, courseId: String, input: ReviewTutorInput) : Response<Unit>{
        return api.reviewTutorOfByCourseId(token, courseId, input)
    }
}