package com.projectprovip.h1eu.giasu.data.course.api

import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.LearningCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.NewCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.CourseByIdDto
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course_detail_dto.LearningCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.request_course.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.review_tutor.ReviewTutorDto
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseApi {
    @GET("Course")
    suspend fun getAllClasses(
        @Query("PageIndex") page: Int,
        @Query("SubjectName") subjectName: String?
    ) : NewCoursesDto

    @GET("Course/{id}")
    suspend fun getCourseById(
        @Path("id") id :String,
    ) : CourseByIdDto

    @POST("Course")
    suspend fun createCourse(
        @Header("Authorization") token: String,
        @Body input: CreateCourseParams
    ) : Response<Unit>

//    @GET("Course/{id}")
//    suspend fun getClassById(@Path("id") id: Int) : CourseInformationDtoItem

    @PUT("Course/{courseId}/RequestCourse")
    suspend fun registerCourse(@Path("courseId") id: String,
                               @Header("Authorization") token: String) : RequestCourseDto
    @GET("Profile/course-requests")
    suspend fun getRequestedCourse(@Header("Authorization") token: String) : RequestCourseDto

    @GET("profile/course-request/{courseRequestId}")
    suspend fun getRequestedCourseDetail(@Path("courseRequestId") courseId: String,
                                         @Header("Authorization") token: String) : RequestedCourseDetailDto

    @GET("Profile/learning-courses")
    suspend fun getLearningCourses(@Header("Authorization") token: String) : LearningCourseDto
    @GET("Profile/learning-course/{courseId}")
    suspend fun getLearningCourseDetail(@Header("Authorization") token: String,
                                        @Path("courseId") courseId: String) : LearningCourseDetailDto
    @PUT("profile/learning-course/{courseId}/review")
    suspend fun reviewTutorOfByCourseId(@Header("Authorization") token: String,
                                        @Path("courseId") id: String,
                                        @Body input: ReviewTutorInput) : ReviewTutorDto
}