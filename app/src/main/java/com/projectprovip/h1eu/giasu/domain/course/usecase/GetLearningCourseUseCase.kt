package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.toLearningCourse
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
            if(response.code() in 200..299) {
                Log.d("Status code", response.code().toString())
                Log.d("respone bodyu", response.body().toString())

                val list = response.body()!!.learningCourseList.map {
                    it.toLearningCourse()
                }
                Log.d("data list", list.toString())
                emit(EDSResult.Success(data = list))
            }
            if(response.code() in 400..499) {
                emit(EDSResult.Error(message = response.errorBody().toString()))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}