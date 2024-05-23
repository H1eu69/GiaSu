package com.projectprovip.h1eu.giasu.domain.tutor.usecase

import android.util.Log
import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.tutor.model.TutorRegisterInput
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterTutorUseCase @Inject constructor(
    private val tutorRepository: TutorRepository
) {
    operator fun invoke(auth: String, input: TutorRegisterInput) = flow<EDSResult<Unit>> {
        try {
            emit(EDSResult.Loading())
            val response = tutorRepository.registerTutor(auth, input)

            if(response.isSuccess) {
                emit(EDSResult.Success(data = null))
            }
            else if(response.isFailure) {
                emit(EDSResult.Error(message = response.error.description))
            }
            else {
                emit(EDSResult.Error(message = "Unexpected error"))
            }
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}