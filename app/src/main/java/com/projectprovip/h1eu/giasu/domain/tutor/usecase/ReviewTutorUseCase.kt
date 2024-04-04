package com.projectprovip.h1eu.giasu.domain.tutor.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.course.dto.review_tutor.ReviewTutorDto
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
    operator fun invoke(auth: String, courseId: String, input: ReviewTutorInput) = flow<EDSResult<ReviewTutorDto>> {
        try {
            emit(EDSResult.Loading())
            val response = coursesRepository.reviewTutorByCourseId(auth, courseId, input)

            if(response.isSuccess) {
                emit(EDSResult.Success(data = null))
            }
            else {
                emit(EDSResult.Error(message = response.error.description))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}