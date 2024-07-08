package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.request_course.toRequestedCourse
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourse
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestedCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    operator fun invoke(token: String) = flow<EDSResult<List<RequestedCourse>>> {
        try {
            emit(EDSResult.Loading())
            val body = repository.getRequestedCourse(token)
            if (body.isSuccess) {
                val requestedCourse = body.value.map {
                    it.toRequestedCourse()
                }
                emit(EDSResult.Success(requestedCourse))
            }
            else {
                emit(EDSResult.Error(body.error.description))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }

}