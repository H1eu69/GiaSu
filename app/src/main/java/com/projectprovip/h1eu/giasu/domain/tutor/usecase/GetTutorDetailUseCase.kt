package com.projectprovip.h1eu.giasu.domain.tutor.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.tutor.dto.toTutorDetail
import com.projectprovip.h1eu.giasu.domain.tutor.model.TutorDetail
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTutorDetailUseCase @Inject constructor(
    private val tutorRepository: TutorRepository
) {
    operator fun invoke(tutorId: Int) = flow<EDSResult<TutorDetail>> {
        try {
            emit(EDSResult.Loading())
            val response = tutorRepository.getTutorDetail(tutorId)

            if (response.code() in 200..300) {
                val data = response.body()!!.tutorDetail.toTutorDetail()
                emit(EDSResult.Success(data = data))
            } else {
                emit(EDSResult.Error(message = response.errorBody()?.string()))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}