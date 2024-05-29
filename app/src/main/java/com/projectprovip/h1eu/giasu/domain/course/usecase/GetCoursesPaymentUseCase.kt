package com.projectprovip.h1eu.giasu.domain.course.usecase

import CoursePayment
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.course_payment.toCoursePayment
import com.projectprovip.h1eu.giasu.data.course.model.CreateCourseParams
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoursesPaymentUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(token: String) = flow<EDSResult<List<CoursePayment>>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.getCoursePayment(token)

            if (response.isSuccess) {
                emit(EDSResult.Success(data = response.value.map {
                    it.toCoursePayment()
                }))
            } else {
                emit(EDSResult.Error(message = response.error.description))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}