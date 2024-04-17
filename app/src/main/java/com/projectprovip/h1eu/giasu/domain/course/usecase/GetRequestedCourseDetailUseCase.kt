package com.projectprovip.h1eu.giasu.domain.course.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.requested_course_detail.toRequestedCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.model.RequestedCourseDetail
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestedCourseDetailUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(id: String, token: String) = flow<EDSResult<RequestedCourseDetail>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.getRequestedCourseDetail(id, token)
            if(response.isSuccess) {
                emit(EDSResult.Success(response.value.toRequestedCourseDetail()))
            } else  {
                emit(EDSResult.Error(response.error.description))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }

    }
}