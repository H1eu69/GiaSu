package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.domain.course.repository.ClassesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RegisterCourseUseCase @Inject constructor(
    private val repository: ClassesRepository
) {
    operator fun invoke(id: Int, token: String?) = flow<Result<RequestCourseDto>> {
        try {
            emit(Result.Loading())
            val response = repository.registerCourse(id, token)
            if(response.body() != null)  {
                emit(Result.Success(response.body()))
            } else  {
                emit(Result.Error(response.errorBody()?.string()))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage))
        }
    }
}