package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.course.dto.RequestedCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.toRequestedCourse
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestedCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    operator fun invoke(token: String) = flow<Result<List<RequestedCourse>>> {
        try {
            emit(Result.Loading())
            val body = repository.getRequestedCourse(token)
            if (body.body() != null) {
                val requestedCourse = body.body()!!.requestedCourseDtoItem.map {
                    it.toRequestedCourse()
                }
                emit(Result.Success(requestedCourse))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage))
        }
    }

}