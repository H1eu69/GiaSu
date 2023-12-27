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
            Log.d("Test state", response.code().toString())
            Log.d("Test state", response.message().toString())

            if(response.code() in 200..300) {
                emit(EDSResult.Success(data = null))
            }
            if(response.code() == 403) {
                emit(EDSResult.Error(message = "You are already tutor"))
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