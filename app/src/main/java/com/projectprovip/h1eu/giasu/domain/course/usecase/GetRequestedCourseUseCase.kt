package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
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
    operator fun invoke(token: String) = flow<EDSResult<List<RequestedCourse>>> {
        try {
            emit(EDSResult.Loading())
            val body = repository.getRequestedCourse(token)
            if (body.code() in 200..299) {
                val requestedCourse = body.body()!!.requestedCourseDtoItem.map {
                    it.toRequestedCourse()
                }
                emit(EDSResult.Success(requestedCourse))
            }
            if(body.code() in 400..499) {
                emit(EDSResult.Error("Error${body.code()} ${body.message()}"))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }

}