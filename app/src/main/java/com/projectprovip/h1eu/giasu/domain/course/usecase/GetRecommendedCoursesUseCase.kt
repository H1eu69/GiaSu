package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.toLearningCourse
import com.projectprovip.h1eu.giasu.data.course.dto.recommend_courses_dto.RecommendedCoursesDto
import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import com.projectprovip.h1eu.giasu.domain.course.repository.RecommendCourseRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetRecommendedCoursesUseCase @Inject constructor(
    private val recommendCourseRepository: RecommendCourseRepository
) {
    operator fun invoke(id: String) = flow<EDSResult<RecommendedCoursesDto>> {
        try {
            emit(EDSResult.Loading())
            val response = recommendCourseRepository.getRecommendCourse(id)
            if (response.isSuccessful) {
                val data = response.body()!!

                Log.d("GetRecommendedCoursesUseCase list", data.toString())
                emit(EDSResult.Success(data = data))
            } else
                emit(EDSResult.Error(message = response.errorBody().toString()))

        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}