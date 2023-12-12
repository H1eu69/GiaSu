package com.projectprovip.h1eu.giasu.domain.tutor.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.model.ReviewTutorInput
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import com.projectprovip.h1eu.giasu.domain.course.repository.CoursesRepository
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReviewTutorUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    operator fun invoke(auth: String, courseId: Int, input: ReviewTutorInput) = flow<EDSResult<Unit>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.reviewTutorByCourseId(auth, courseId, input)

            if(response.code() in 200..300) {
                emit(EDSResult.Success(data = null))
            }
            else {
                emit(EDSResult.Error(message = response.message()))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}