package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.toLearningCourse
import com.projectprovip.h1eu.giasu.domain.course.repository.RecommendCourseRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecommendCoursesByUserIdUseCase @Inject constructor(
    private val recommendCoursesRepository: RecommendCourseRepository
) {
    operator fun invoke(userId: String) = flow<EDSResult<List<String>>> {
        try {
            emit(EDSResult.Loading())
            val response = recommendCoursesRepository.getRecommendCoursesByUserId(userId)
            if (response.isSuccessful) {
                val list = response.body()!!.data
                Log.d("GetRecommendCoursesByUserIdUseCase", list.toString())
                emit(EDSResult.Success(data = list))
            } else
                emit(EDSResult.Error(message = response.message()))

        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}