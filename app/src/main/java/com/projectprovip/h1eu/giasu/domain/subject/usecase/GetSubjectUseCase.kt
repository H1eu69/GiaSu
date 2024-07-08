package com.projectprovip.h1eu.giasu.domain.subject.usecase

import com.projectprovip.h1eu.giasu.common.EDSResult
import com.projectprovip.h1eu.giasu.data.subject.dto.toSubject
import com.projectprovip.h1eu.giasu.domain.subject.model.Subject
import com.projectprovip.h1eu.giasu.domain.subject.repository.SubjectRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSubjectUseCase  @Inject constructor(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke() = flow<EDSResult<List<Subject>>> {
        try {
            emit(EDSResult.Loading())
            val data = subjectRepository.getSubject().value.map {
                it.toSubject()
            }
            emit(EDSResult.Success(data))
        } catch (e: HttpException) {
            emit(EDSResult.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(EDSResult.Error(e.localizedMessage))
        }
    }
}