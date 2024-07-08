package com.projectprovip.h1eu.giasu.domain.course.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.RegisterCourseDto
import com.projectprovip.h1eu.giasu.data.course.dto.notify_course_payment.NotifyCoursePaymentDto
import com.projectprovip.h1eu.giasu.data.course.dto.request_course.RequestCourseDto
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotifyCoursePaymentUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    operator fun invoke(id: String, token: String) = flow<EDSResult<NotifyCoursePaymentDto>> {
        try {
            emit(EDSResult.Loading())
            val response = repository.notifyCoursePayment(id, token)
//            if (response.code() == 403) {
//                emit(EDSResult.Error("Please register to be tutor before take courses"))
//            } else
            if (response.isSuccess){
                emit(EDSResult.Success(response))
            } else {
                emit(EDSResult.Error(response.error.description))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}