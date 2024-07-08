package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.learning_course.toLearningCourse
import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourse
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLearningCourseUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(token: String) = flow<EDSResult<List<LearningCourse>>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.getLearningCourses(token)
            if (response.isSuccess) {

                val list = response.value.map {
                    it.toLearningCourse()
                }
                Log.d("GetLearningCourseUseCase list", list.toString())
                emit(EDSResult.Success(data = list))
            } else
                emit(EDSResult.Error(message = response.error.description))

        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}