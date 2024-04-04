package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.learningCourseDetailDto.toLearningCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.model.LearningCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLearningCourseDetailUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(token: String, courseId: String) = flow<EDSResult<LearningCourseDetail>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.getLearningCourseDetail(token, courseId)
            Log.d("Test GetLearningCourseDetailUseCase", response.toString())
            Log.d("Test GetLearningCourseDetailUseCase", courseId.toString())
            Log.d("Test GetLearningCourseDetailUseCase", response.isSuccess.toString())

            if(response.isSuccess) {
                emit(EDSResult.Success(data = response.value.toLearningCourseDetail()))
            }
            else {
                emit(EDSResult.Error(message = response.error.description))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}