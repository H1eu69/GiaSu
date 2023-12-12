package com.projectprovip.h1eu.giasu.data.course.repository

import com.projectprovip.h1eu.giasu.data.course.api.CourseApi
import com.projectprovip.h1eu.giasu.data.course.dto.CoursesInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.LearningCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import retrofit2.Response
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi
) : CoursesRepository{
    override suspend fun getAllClasses(): CoursesInformationDto {
        return api.getAllClasses()
    }

    override suspend fun registerCourse(id: Int, token: String?): Response<RequestCourseDto> {
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

    override suspend fun getLearningCourses(token: String): Response<LearningCoursesDto> {
        return api.getLearningCourses(token)
    }

    override suspend fun reviewTutorByCourseId(token: String, courseId: Int, input: ReviewTutorInput) : Response<Unit>{
        return api.reviewTutorOfByCourseId(token, courseId, input)
    }
}