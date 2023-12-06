package com.projectprovip.h1eu.giasu.domain.tutor.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.tutor.dto.toTutor
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllTutorUseCase @Inject constructor(
    private val tutorRepository: TutorRepository
) {
    operator fun invoke(pageIndex : Int = 1) = flow<EDSResult<List<Tutor>>> {
        try {
            emit(EDSResult.Loading())
            val data = tutorRepository.getAllTutor(pageIndex).tutorInformation.map {
                it.toTutor()
            }
            emit(EDSResult.Success(data))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}