package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.RequestCourseDto
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    operator fun invoke(id: String, token: String?) = flow<EDSResult<RequestCourseDto>> {
        try {
            emit(EDSResult.Loading())
            val response = repository.registerCourse(id, token)
            if (response.code() == 403) {
                emit(EDSResult.Error("Please register to be tutor before take courses"))
            } else if (response.body() != null) {
                emit(EDSResult.Success(response.body()))
            } else {
                emit(EDSResult.Error(response.errorBody()?.string()))
            }
            response.code()
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}