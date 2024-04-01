package com.projectprovip.h1eu.giasu.data.course.api

import com.projectprovip.h1eu.giasu.data.course.dto.LearningCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.LearningCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.new_courses.NewCoursesDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDetailDto
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.course_by_id.CourseByIdDto
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseInput
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

    @POST("Course/Create")
    suspend fun createCourse(
        @Header("Authorization") token: String,
        @Body input: CreateCourseInput
    ) : Response<Unit>

//    @GET("Course/{id}")
//    suspend fun getClassById(@Path("id") id: Int) : CourseInformationDtoItem

    @PUT("Course/{courseId}/RequestCourse")
    suspend fun registerCourse(@Path("courseId") id: String,
                               @Header("Authorization") token: String) : Response<RequestCourseDto>
    @GET("Profile/GetCourseRequests")
    suspend fun getRequestedCourse(@Header("Authorization") token: String) : Response<RequestedCourseDto>

    @GET("Profile/CourseRequestDetail/{id}")
    suspend fun getRequestedCourseDetail(@Path("id") courseId: Int,
                                         @Header("Authorization") token: String) : Response<RequestedCourseDetailDto>

    @GET("Profile/learning-courses")
    suspend fun getLearningCourses(@Header("Authorization") token: String) : Response<LearningCourseDto>
    @GET("Profile/GetLearningCourse/{id}")
    suspend fun getLearningCourseDetail(@Header("Authorization") token: String,
                                        @Path("id") courseId: String) : Response<LearningCourseDetailDto>
    @POST("Profile/Review/{id}")
    suspend fun reviewTutorOfByCourseId(@Header("Authorization") token: String,
                                        @Path("id") id: String,
                                        @Body input: ReviewTutorInput) : Response<Unit>
}