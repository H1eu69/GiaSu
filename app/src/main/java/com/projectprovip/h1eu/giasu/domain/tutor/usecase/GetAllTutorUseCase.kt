package com.projectprovip.h1eu.giasu.domain.tutor.usecase

import com.projectprovip.h1eu.giasu.common.Result
import com.projectprovip.h1eu.giasu.data.tutor.dto.toTutor
import com.projectprovip.h1eu.giasu.domain.tutor.model.Tutor
import com.projectprovip.h1eu.giasu.domain.tutor.repository.TutorRepository
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllTutorUseCase @Inject constructor(
    private val tutorRepository: TutorRepository
) {
    operator fun invoke(pageIndex : Int = 1) = flow<Result<List<Tutor>>> {
        try {
            emit(Result.Loading())
            val data = tutorRepository.getAllTutor(pageIndex).tutorInformation.map {
                it.toTutor()
            }
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage))
        }
    }.debounce(3000)
}