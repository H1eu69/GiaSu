package com.projectprovip.h1eu.giasu.data.course.api

import com.projectprovip.h1eu.giasu.data.course.dto.CoursesInformationDto
import com.projectprovip.h1eu.giasu.data.course.dto.LearningCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CourseApi {
    @GET("Course")
    suspend fun getAllClasses() : CoursesInformationDto

//    @GET("Course/{id}")
//    suspend fun getClassById(@Path("id") id: Int) : CourseInformationDtoItem

    @PUT("Course/{courseId}/RequestCourse")
    suspend fun registerCourse(@Path("courseId") id: Int,
                               @Header("Authorization") token: String) : Response<RequestCourseDto>
    @GET("Profile/GetCourseRequests")
    suspend fun getRequestedCourse(@Header("Authorization") token: String) : Response<RequestedCourseDto>

    @GET("Profile/CourseRequestDetail/{id}")
    suspend fun getRequestedCourseDetail(@Path("id") courseId: Int,
                                         @Header("Authorization") token: String) : Response<RequestedCourseDetailDto>

    @GET("Profile/GetLearningCourses")
    suspend fun getLearningCourses(@Header("Authorization") token: String) : Response<LearningCoursesDto>

    @POST("Profile/Review/{id}")
    suspend fun reviewTutorOfByCourseId(@Header("Authorization") token: String,
                                        @Path("id") id: Int,
                                        @Body input: ReviewTutorInput) : Response<Unit>
}