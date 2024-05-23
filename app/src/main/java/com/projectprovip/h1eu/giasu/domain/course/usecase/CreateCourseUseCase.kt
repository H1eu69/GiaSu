package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateCourseUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(token: String, input: CreateCourseParams) = flow<EDSResult<Unit>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.createCourse(token, input)

            if(response.isSuccessful) {
                emit(EDSResult.Success(data = null))
            } else {
                emit(EDSResult.Error(message = response.message()))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}